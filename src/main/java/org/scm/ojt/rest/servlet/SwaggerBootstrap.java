package org.scm.ojt.rest.servlet;

import com.google.inject.Singleton;
import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.models.Swagger;
import io.swagger.models.auth.AuthorizationValue;
import io.swagger.models.auth.BasicAuthDefinition;
import io.swagger.models.auth.OAuth2Definition;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

@Singleton
public class SwaggerBootstrap extends HttpServlet {

  @Override
  public void init(final ServletConfig config) throws ServletException {
    Swagger swagger = new Swagger();
    //swagger.securityDefinition("oauth2",new OAuth2Definition("/v1/user/login"));
    swagger.securityDefinition("basicAuth", new BasicAuthDefinition());
    new SwaggerContextService().withServletConfig(config).updateSwagger(swagger);
  }
}