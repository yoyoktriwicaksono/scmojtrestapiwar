package org.scm.ojt.rest.filter;

import org.scm.ojt.rest.utils.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
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

    public static final String HEADER_PROPERTY_ID = "id";

    // https://github.com/maltesander/rest-jersey2-json-jwt-authentication/blob/master/src/main/java/com/tutorialacademy/rest/filter/AuthenticationFilter.java

    @Override
    public void filter( ContainerRequestContext requestContext ){
        Method method = resourceInfo.getResourceMethod();

        Boolean isSwagger = false;
        if (method.getName().equalsIgnoreCase("getListing") && method.getDeclaringClass().getName().equalsIgnoreCase("io.swagger.jaxrs.listing.ApiListingResource")){
            isSwagger = true;
        }
        if( !method.isAnnotationPresent( PermitAll.class ) && !isSwagger ){
            requestContext.abortWith(
                    ResponseBuilder.createResponse( Response.Status.UNAUTHORIZED, "ACCESS_DENIED" )
            );
            return;
        }

        // get request headers to extract jwt token
        final MultivaluedMap<String, String> headers = requestContext.getHeaders();

        // set header param email for user identification in rest service - do not decode jwt twice in rest services
        List<String> idList = new ArrayList<String>();
        idList.add( "tokenJWT" );
        headers.put( HEADER_PROPERTY_ID, idList );
    }
}
