package org.scm.ojt.rest.services;

import com.google.inject.Singleton;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.scm.ojt.rest.dto.CustomerDTO;
import org.scm.ojt.rest.dto.UserDTO;
import org.scm.ojt.rest.logic.UserLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    @ApiOperation(value="List All User")
    public List<UserDTO> listAll(
    ) {
        LOG.info("List All User");
        return userLogic.listAll();
    }

}
