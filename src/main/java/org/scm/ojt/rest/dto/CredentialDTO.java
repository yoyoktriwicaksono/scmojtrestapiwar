package org.scm.ojt.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Yoyok_T on 07/02/2019.
 */
@ApiModel(description = "Credential")
@Data
@EqualsAndHashCode(callSuper = false)
public class CredentialDTO {
    @ApiModelProperty(dataType = "java.lang.String" ,value = "email", example = "testz@gmail.com")
    private String email;

    @ApiModelProperty(dataType = "java.lang.String" ,value = "password", example = "rahasi4")
    private String password;

    @JsonCreator
    public CredentialDTO(){

    }

    @JsonCreator
    public CredentialDTO(
            @JsonProperty("email") final String email,
            @JsonProperty("password") final String password
    ){
        this.email = email;
        this.password = password;
    }

}
