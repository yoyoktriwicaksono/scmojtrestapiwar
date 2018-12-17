package org.scm.ojt.rest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.classpath.ClasspathConfigurationSource;
import org.cfg4j.source.context.filesprovider.ConfigFilesProvider;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by Yoyok_T on 28/09/2018.
 */
public class ConfigurationManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationManager.class);

    static final Object singletonLockObject = new Object();
    static ConfigurationManager instance;
    private AppConfigData appConfigData = null;
    private MongoConfigData mongoConfigData = null;
    private SwaggerConfigData swaggerConfigData = null;

    private ConfigurationManager(){
        // we have to set the variable of heroku : STAGING : PRODUCTION or QA
        String environment = System.getenv("STAGING");
        logger.info("Load Heroku environment variable =  " + environment);
        ConfigFilesProvider configFilesProvider = () -> Arrays.asList(
                Paths.get("app.properties"),
                Paths.get("database.properties")
        );

        if (environment != null) {
            // Specify which files to load. Configuration from both files will be merged.
            if (environment.equalsIgnoreCase("PRODUCTION")) {
                configFilesProvider = () -> Arrays.asList(
                        Paths.get("app.production.properties"),
                        Paths.get("database.production.properties")
                );
            } else if(environment.equalsIgnoreCase("QA")) {
                configFilesProvider = () -> Arrays.asList(
                        Paths.get("app.qa.properties"),
                        Paths.get("database.qa.properties")
                );
            }
        }

        // Use classpath as configuration store
        ConfigurationSource source = new ClasspathConfigurationSource(configFilesProvider);

        // Create provider
        ConfigurationProvider configProvider = new ConfigurationProviderBuilder()
                .withConfigurationSource(source)
                .build();

        logger.info("Load app.properties");
        appConfigData = configProvider.bind("app", AppConfigData.class);
        swaggerConfigData = configProvider.bind("swagger", SwaggerConfigData.class);
        logger.info("Load database.properties");
        mongoConfigData = configProvider.bind("mongo", MongoConfigData.class);
    }

    public static ConfigurationManager getInstance(){
        if( instance == null ){
            synchronized (singletonLockObject){
                if( instance == null ){
                    logger.info("Load Configuration Manager");
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }

    public AppConfigData getAppConfigData(){
        return appConfigData;
    }

    public MongoConfigData getMongoConfigData(){
        return mongoConfigData;
    }

    public SwaggerConfigData getSwaggerConfigData(){
        return swaggerConfigData;
    }
}
