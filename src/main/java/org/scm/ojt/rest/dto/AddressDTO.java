package org.scm.ojt.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Yoyok_T on 18/10/2018.
 */
@Data
public class AddressDTO {
    @ApiModelProperty(dataType = "java.lang.String" ,value = "street", example = "Jl Juanda")
    private String street;

    @ApiModelProperty(dataType = "java.lang.String" ,value = "city", example = "Bandung")
    private String city;

    @ApiModelProperty(dataType = "java.lang.String" ,value = "state", example = "West Java")
    private String state;

    @ApiModelProperty(dataType = "java.lang.String" ,value = "zipCode", example = "65181")
    private String zipCode;
}
