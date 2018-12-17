package org.scm.ojt.rest.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mongodb.morphia.annotations.*;

/**
 * Created by Yoyok_T on 11/10/2018.
 */
@Entity(value = "Supplier", noClassnameStored = true)
@Indexes(
        @Index(value = "SupplierID", fields = @Field("SupplierID"))
)
@Data
public class Supplier extends BaseEntity {

    @Property("supplierID")
    private String SupplierID;

    @Property("name")
    private String Name;

    @Property("address")
    private String Address;

    @Property("city")
    private String City;

    @Property("phone")
    private String Phone;

}
