package org.scm.ojt.rest.services;

import com.google.inject.Singleton;
import io.swagger.annotations.Api;
import org.scm.ojt.rest.dto.SubscriptionDTO;
import org.scm.ojt.rest.exception.UserExistingException;
import org.scm.ojt.rest.exception.UserNotFoundException;
import org.scm.ojt.rest.logic.CustomerLogic;
import org.scm.ojt.rest.logic.SubscriptionLogic;
import org.scm.ojt.rest.utils.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Wicaksono on 1/30/2019.
 */
@Singleton
@Api("Auth Service")
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthService {
    private static final Logger LOG = LoggerFactory.getLogger(AuthService.class);

    private final SubscriptionLogic subscriptionLogic;

    @Inject
    public AuthService(SubscriptionLogic subscriptionLogic){
        this.subscriptionLogic = subscriptionLogic;
    }

    // https://tutorial-academy.com/rest-jersey2-json-jwt-authentication-authorization/
    @POST
    @Path("/signup")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(SubscriptionDTO subscription) {
        try{
            try{
                SubscriptionDTO subscriptionDTO = subscriptionLogic.getById(subscription.getEmail());
                if (subscriptionDTO == null){
                    throw new UserExistingException(subscription.getEmail());
                }
                return Response.ok().entity(subscriptionDTO).build();
            }
            catch (UserNotFoundException e){
                return ResponseBuilder.createResponse(Response.Status.CONFLICT, e.getMessage());
            }

        }
        catch (UserExistingException e){
            return ResponseBuilder.createResponse(Response.Status.CONFLICT, e.getMessage());
        }
        catch (Exception e){
            return ResponseBuilder.createResponse(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
