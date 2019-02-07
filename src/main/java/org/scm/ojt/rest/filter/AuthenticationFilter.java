package org.scm.ojt.rest.filter;

import org.jose4j.jwt.consumer.InvalidJwtException;
import org.scm.ojt.rest.security.TokenSecurity;
import org.scm.ojt.rest.utils.AppConstants;
import org.scm.ojt.rest.utils.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.servlet.ServletException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yoyok_T on 29/01/2019.
 */
@Provider
@Priority( Priorities.AUTHENTICATION )
public class AuthenticationFilter implements javax.ws.rs.container.ContainerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Context
    private ResourceInfo resourceInfo;

    //public static final String HEADER_PROPERTY_ID = "id";

    // https://github.com/maltesander/rest-jersey2-json-jwt-authentication/blob/master/src/main/java/com/tutorialacademy/rest/filter/AuthenticationFilter.java

    @Override
    public void filter( ContainerRequestContext requestContext ){
        Method method = resourceInfo.getResourceMethod();

        Boolean isSwagger = false;
        if (method.getName().equalsIgnoreCase("getListing") && method.getDeclaringClass().getName().equalsIgnoreCase("io.swagger.jaxrs.listing.ApiListingResource")){
            isSwagger = true;
        }
        // Allow it for swagger
        if( !method.isAnnotationPresent( PermitAll.class ) && !isSwagger ){
            // if it set to denyall, the it is secret, but why we need it ?
            if( method.isAnnotationPresent( DenyAll.class ) )
            {
                requestContext.abortWith(
                        ResponseBuilder.createResponse( Response.Status.FORBIDDEN, AppConstants.Authentication.ACCESS_FORBIDDEN )
                );
                return;
            }

            requestContext.abortWith(
                    ResponseBuilder.createResponse( Response.Status.UNAUTHORIZED, "ACCESS_DENIED" )
            );
            return;
        }

        // get token from header
        final MultivaluedMap<String, String> headers = requestContext.getHeaders();
        final List<String> authProperty = headers.get( AppConstants.Authentication.AUTHORIZATION_PROPERTY );

        // block request if token is not provided
        if( authProperty == null || authProperty.isEmpty() )
        {
            LOG.warn("No token provided!");
            requestContext.abortWith(
                    ResponseBuilder.createResponse( Response.Status.UNAUTHORIZED, AppConstants.Authentication.ACCESS_DENIED )
            );
            return;
        }

        String id = null ;
        String jwt = authProperty.get(0);

        // Decode it, block if invalid
        try {
            id = TokenSecurity.validateJwtToken( jwt );
        } catch ( InvalidJwtException e ) {
            LOG.warn("Invalid token provided!");
            requestContext.abortWith(
                    ResponseBuilder.createResponse( Response.Status.UNAUTHORIZED, AppConstants.Authentication.ACCESS_INVALID_TOKEN )
            );
            return;
        }

        // set header param email for user identification in rest service - do not decode jwt twice in rest services
        List<String> idList = new ArrayList<String>();
        idList.add( "tokenJWT" );
        headers.put(AppConstants.Authentication.HEADER_PROPERTY_ID, idList );
    }
}
