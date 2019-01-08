package org.scm.ojt.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Yoyok_T on 17/12/2018.
 */
@ApiModel(description = "User")
@Data
public class UserDTO {
    @ApiModelProperty(dataType = "java.lang.Integer" ,value = "ID", example = "1")
    private Integer id;
    @ApiModelProperty(dataType = "java.lang.String" ,value = "name", example = "Yoyok Tri Wicaksono")
    private String name;
    @ApiModelProperty(dataType = "java.lang.String" ,value = "email", example = "test@gmail.com")
    private String email;
}
