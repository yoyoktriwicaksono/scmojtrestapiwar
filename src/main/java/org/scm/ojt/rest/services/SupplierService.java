package org.scm.ojt.rest.services;

import com.google.inject.Singleton;
import io.swagger.annotations.*;
import org.scm.ojt.rest.dto.CustomerDTO;
import org.scm.ojt.rest.dto.SupplierDTO;
import org.scm.ojt.rest.logic.SupplierLogic;
import org.scm.ojt.rest.entity.Supplier;
import org.scm.ojt.rest.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.scm.ojt.rest.utils.*;

/**
 * Created by Yoyok_T on 11/10/2018.
 */
@Singleton
@Api("Suppplier Service")
@Path("/supplier")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SupplierService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    private SupplierLogic supplierLogic;

    @Inject
    public SupplierService(SupplierLogic supplierLogic){
        this.supplierLogic = supplierLogic;
    }

    @GET
    @ApiOperation(value="Search Supplier")
    public List<SupplierDTO> searchSupplier(
            @ApiParam(value = "Supplier ID", required = true) @QueryParam("supplierID") final String supplierID,
            @ApiParam(value = "Supplier Name", required = true) @QueryParam("name") final String supplierName,
            @ApiParam(value = "Phone Number", required = true) @QueryParam("phone") final String phoneNumber
    ) {
        LOG.info("Search Customer");
        return supplierLogic.search(supplierID, supplierName, phoneNumber);
    }

    @GET
    @Path("{id}")
    @ApiOperation(value="Get Supplier", response = SupplierDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID"),
            @ApiResponse(code = 204, message = "Supplier Not Found"),
            @ApiResponse(code = 500, message = "Something wrong in Server")
    })
    public SupplierDTO getById(
            @ApiParam(value = "id", required = true) @PathParam("id") final String id
    ) {
        return supplierLogic.getById(id);
    }

    @POST
    @ApiOperation(value="Create Supplier")
    public SupplierDTO createSupplier(final SupplierDTO supplierDTO){
        return supplierLogic.create(supplierDTO);
    }

    @PUT
    @Path("{id}")
    @ApiOperation(value="Update Supplier")
    public SupplierDTO updateSupplier(
            @ApiParam(value = "id", required = true) @PathParam("id") String id,
            final SupplierDTO supplierDTO){
        return supplierLogic.updateSupplier(id, supplierDTO);
    }

    @DELETE
    @Path("{id}")
    @ApiOperation(value="Remove Supplier")
    public Response deleteById(
            @ApiParam(value = "id", required = true) @PathParam("id") final String id
    ){
        return Response.ok().entity(supplierLogic.deleteSupplier(id)).build();
    }

}
