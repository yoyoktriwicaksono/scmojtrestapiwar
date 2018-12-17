package org.scm.ojt.rest.services;

import com.google.inject.Singleton;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.scm.ojt.rest.dto.CustomerDTO;
import org.scm.ojt.rest.logic.CustomerLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Yoyok_T on 18/10/2018.
 */
@Singleton
@Api("Customer Service")
@Path("/customer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerLogic customerLogic;

    @Inject
    public CustomerService(CustomerLogic customerLogic){
        this.customerLogic = customerLogic;
    }

    @GET
    @ApiOperation(value="Search Customer")
    public List<CustomerDTO> searchCustomer(
            @ApiParam(value = "Customer Name", required = true) @QueryParam("customerName") final String customerName,
            @ApiParam(value = "Phone Number", required = true) @QueryParam("phoneNumber") final String phoneNumber,
            @ApiParam(value = "Email", required = true) @QueryParam("email") final String email
    ) {
        LOG.info("Search Customer");
        return customerLogic.search(customerName, phoneNumber, email);
    }

    @GET
    @Path("{id}")
    @ApiOperation(value="Get Customer")
    public CustomerDTO getById(
            @ApiParam(value = "id", required = true) @PathParam("id") final String id
    ){
        return customerLogic.getById(id);
    }

    @POST
    @ApiOperation(value="Create Customer")
    public CustomerDTO createCustomer(final CustomerDTO customerDTO){
        return customerLogic.create(customerDTO);
    }

    @PUT
    @Path("{id}")
    @ApiOperation(value="Update Customer")
    public CustomerDTO updateCustomer(
            @ApiParam(value = "id", required = true) @PathParam("id") String id,
            final CustomerDTO customerDTO){
        return customerLogic.updateCustomer(id, customerDTO);
    }

    @DELETE
    @Path("{id}")
    @ApiOperation(value="Remove Customer")
    public Response deleteById(
            @ApiParam(value = "id", required = true) @PathParam("id") final String id
    ){
        return Response.ok().entity(customerLogic.deleteCustomer(id)).build();
    }

}
