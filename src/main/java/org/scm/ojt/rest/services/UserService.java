package org.scm.ojt.rest.services;

import com.google.inject.Singleton;
import io.swagger.annotations.*;
import org.scm.ojt.rest.dto.CustomerDTO;
import org.scm.ojt.rest.dto.SupplierDTO;
import org.scm.ojt.rest.dto.UserDTO;
import org.scm.ojt.rest.logic.UserLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Yoyok_T on 17/12/2018.
 */
@Singleton
@Api("User Service")
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private UserLogic userLogic;

    @Inject
    public UserService(UserLogic userLogic){
        this.userLogic = userLogic;
    }

    @GET
    @PermitAll
    @ApiOperation(value="List All User",authorizations = {@Authorization(value="basicAuth")})
    public List<UserDTO> listAll(
    ) {
        LOG.info("List All User");
        return userLogic.listAll();
    }

    @GET
    @Path("{id}")
    @ApiOperation(value="Get User", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID"),
            @ApiResponse(code = 204, message = "User Not Found"),
            @ApiResponse(code = 500, message = "Something wrong in Server")
    })
    public UserDTO getById(
            @ApiParam(value = "id", required = true) @PathParam("id") final Integer id
    ) {
        return userLogic.findById(id);
    }

    @POST
    @PermitAll
    @ApiOperation(value="Create User", response = UserDTO.class, authorizations = {@Authorization(value="basicAuth")})
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Something wrong in Server")
    })
    public UserDTO createUser(final UserDTO userDTO){
        return userLogic.create(userDTO);
    }

    @DELETE
    @Path("{id}")
    @ApiOperation(value="Remove User")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Something wrong in Server")
    })
    public Response deleteById(
            @ApiParam(value = "id", required = true) @PathParam("id") final Integer id
    ){
        return Response.ok().entity(userLogic.delete(id)).build();
    }

    @PUT
    @Path("{id}")
    @ApiOperation(value="Update User")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID"),
            @ApiResponse(code = 204, message = "User Not Found"),
            @ApiResponse(code = 500, message = "Something wrong in Server")
    })
    public UserDTO updateUser(
            @ApiParam(value = "id", required = true) @PathParam("id") Integer id,
            final UserDTO userDTO){
        return userLogic.update(id, userDTO);
    }

}
