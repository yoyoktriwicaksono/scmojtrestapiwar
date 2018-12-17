package org.scm.ojt.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Yoyok_T on 18/10/2018.
 */
@Data
public abstract class BaseDTO {
    @ApiModelProperty(dataType = "java.lang.String" ,value = "id", required = true, example = "5bc5bded152608f1245d2636")
    protected String id;
    @ApiModelProperty(dataType = "java.lang.Long" ,value = "version", required = true, example = "1")
    protected Long version;
}
