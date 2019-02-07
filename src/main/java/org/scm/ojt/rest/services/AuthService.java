package org.scm.ojt.rest.services;

import com.google.inject.Singleton;
import io.swagger.annotations.Api;
import org.scm.ojt.rest.dto.CredentialDTO;
import org.scm.ojt.rest.dto.SubscriptionDTO;
import org.scm.ojt.rest.exception.UserExistingException;
import org.scm.ojt.rest.exception.UserNotFoundException;
import org.scm.ojt.rest.filter.AuthenticationFilter;
import org.scm.ojt.rest.logic.CustomerLogic;
import org.scm.ojt.rest.logic.SubscriptionLogic;
import org.scm.ojt.rest.security.PasswordSecurity;
import org.scm.ojt.rest.security.TokenSecurity;
import org.scm.ojt.rest.utils.AppConstants;
import org.scm.ojt.rest.utils.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wicaksono on 1/30/2019.
 */
@Singleton
@DeclareRoles({AppConstants.Role.ROLE_ADMIN, AppConstants.Role.ROLE_USER, AppConstants.Role.ROLE_GUEST})
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
            SubscriptionDTO subscriptionDTO = subscriptionLogic.getSubscriptionByEmail(subscription.getEmail());
            if (subscriptionDTO != null){
                throw new UserExistingException(subscription.getEmail());
            }
            // register user
            subscription.setRole(AppConstants.Role.ROLE_USER);
            String plainPassword = subscription.getPassword();
            subscription.setPassword(PasswordSecurity.generateHash(plainPassword));
            SubscriptionDTO signupUser = subscriptionLogic.signUp(subscription);
            if (signupUser != null) {
                return Response.ok().entity(subscriptionDTO).build();
            } else {
                return ResponseBuilder.createResponse(Response.Status.INTERNAL_SERVER_ERROR);
            }
        }
        catch (UserExistingException e){
            return ResponseBuilder.createResponse(Response.Status.CONFLICT, e.getMessage());
        }
        catch (Exception e){
            return ResponseBuilder.createResponse(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Path("/authenticate")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(CredentialDTO credential) {
        try {
            SubscriptionDTO subscriptionDTO = subscriptionLogic.getSubscriptionByEmail(credential.getEmail());
            if (subscriptionDTO != null){
                throw new UserExistingException(credential.getEmail());
            }
            if( PasswordSecurity.validatePassword( credential.getPassword(), subscriptionDTO.getPassword() ) == false ) {
                return ResponseBuilder.createResponse( Response.Status.UNAUTHORIZED );
            }
            // generate a token for the user
            String token = TokenSecurity.generateJwtToken( subscriptionDTO.getId() );

            //TODO : Update user subscription with new token
//            // write the token to the database
//            UserSecurity sec = new UserSecurity( null, token );
//            sec.setId( id );
//            userDao.setUserAuthentication( sec );

            Map<String,Object> map = new HashMap<String,Object>();
            map.put( AppConstants.Authentication.AUTHORIZATION_PROPERTY, token );

            // Return the token on the response
            return ResponseBuilder.createResponse( Response.Status.OK, map );

        }
        catch( UserNotFoundException e ) {
            return ResponseBuilder.createResponse( Response.Status.NOT_FOUND, e.getMessage() );
        }
        catch( Exception e ) {
            return ResponseBuilder.createResponse( Response.Status.UNAUTHORIZED );
        }
    }
}
