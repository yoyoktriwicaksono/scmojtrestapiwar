package org.scm.ojt.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mongodb.morphia.annotations.Property;

/**
 * Created by Wicaksono on 1/30/2019.
 */
@ApiModel(description = "Subscription")
@Data
@EqualsAndHashCode(callSuper = false)
public class SubscriptionDTO extends BaseDTO {
    @ApiModelProperty(dataType = "java.lang.String" ,value = "email", example = "testz@gmail.com")
    private String email;

    @ApiModelProperty(dataType = "java.lang.String" ,value = "name", example = "Yoyok Tri Wicaksono")
    private String name;

    @ApiModelProperty(dataType = "java.lang.String" ,value = "password", example = "rahasi4")
    private String password;

    @JsonCreator
    public SubscriptionDTO(){

    }

    @JsonCreator
    public SubscriptionDTO(
            @JsonProperty("email") final String email,
            @JsonProperty("name") final String name,
            @JsonProperty("password") final String password
    ){
        this.email = email;
        this.name = name;
        this.password = password;
    }

}
