package org.scm.ojt.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Yoyok_T on 19/10/2018.
 */
@ApiModel(description = "Supplier")
@Data
public class SupplierDTO extends BaseDTO {
    @ApiModelProperty(dataType = "java.lang.String" ,value = "supplierID", example = "S001")
    private String supplierID;

    @ApiModelProperty(dataType = "java.lang.String" ,value = "name", example = "Kimia Farma")
    private String name;

    @ApiModelProperty(dataType = "java.lang.String" ,value = "address", example = "Jl Veteran 100")
    private String address;

    @ApiModelProperty(dataType = "java.lang.String" ,value = "city", example = "Bandung")
    private String city;

    @ApiModelProperty(dataType = "java.lang.String" ,value = "phone", example = "022424134")
    private String phone;

    @JsonCreator
    public SupplierDTO(){

    }

    @JsonCreator
    public SupplierDTO(
            @JsonProperty("id") final String id,
            @JsonProperty("version") final Long version,
            @JsonProperty("supplierID") final String supplierID,
            @JsonProperty("name") final String name,
            @JsonProperty("address") final String address,
            @JsonProperty("city") final String city,
            @JsonProperty("phone") final String phone
    ){
        this.id = id;
        this.version = version;
        this.supplierID = supplierID;
        this.name = name;
        this.address = address;
        this.city = city;
        this.phone = phone;
    }

}
