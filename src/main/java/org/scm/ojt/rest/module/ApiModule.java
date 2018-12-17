package org.scm.ojt.rest.module;

import org.scm.ojt.rest.config.ConfigurationManager;
import org.scm.ojt.rest.config.SwaggerConfigData;
import org.scm.ojt.rest.dao.ConnectionManager;
import org.scm.ojt.rest.filter.CrossOriginResourceSharingFilter;
import org.scm.ojt.rest.servlet.ApiApplication;
import org.scm.ojt.rest.servlet.SwaggerBootstrap;
import com.google.inject.servlet.ServletModule;
import io.swagger.jaxrs.config.BeanConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.scm.ojt.rest.utils.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ApiModule extends ServletModule {
    private static final Logger LOG = LoggerFactory.getLogger(ApiModule.class);

    @Override
    protected void configureServlets() {
        LOG.info("Configuring Servlets...");
        bind(ConnectionManager.class).asEagerSingleton();
        bind(ServletContainer.class).asEagerSingleton();
        bind(CrossOriginResourceSharingFilter.class).asEagerSingleton();

        initializeApplicationServlet();
        initializeSwaggerBootstrap(ConfigurationManager.getInstance().getSwaggerConfigData());

        // CORS Filter
        filter("/*").through(CrossOriginResourceSharingFilter.class);
    }

    private void initializeApplicationServlet() {
        final Map<String, String> props = new HashMap<>();
        props.put(AppConstants.JAVAXRS, ApiApplication.class.getName());
        props.put(AppConstants.JERSEYCONFIG, Boolean.TRUE.toString());
        serve("/v1/*").with(ServletContainer.class, props);
    }

    private void initializeSwaggerBootstrap(SwaggerConfigData swaggerConfigData) {
        LOG.info("Configuring Swagger...");
        serve("").with(SwaggerBootstrap.class);
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion(swaggerConfigData.version());
        beanConfig.setTitle(swaggerConfigData.title());
        beanConfig.setDescription(swaggerConfigData.description());
        LOG.info("Heroku PORT = "+System.getenv("PORT"));
        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = ConfigurationManager.getInstance().getAppConfigData().port().toString();
            beanConfig.setSchemes(new String[]{"http"});
            beanConfig.setHost(ConfigurationManager.getInstance().getAppConfigData().host() + ":" + webPort.toString());
        } else {
            // Heroku don't need port and host shall be https
            beanConfig.setSchemes(new String[]{"https"});
            beanConfig.setHost(ConfigurationManager.getInstance().getAppConfigData().host());
        }
        beanConfig.setBasePath("/v1");
        beanConfig.setResourcePackage(AppConstants.SERVICEPACKAGE);
        beanConfig.setScan(true);
    }
}