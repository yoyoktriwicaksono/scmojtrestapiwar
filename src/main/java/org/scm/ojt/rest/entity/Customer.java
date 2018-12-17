package org.scm.ojt.rest.entity;

import lombok.Data;
import org.mongodb.morphia.annotations.*;

/**
 * Created by Yoyok_T on 18/10/2018.
 */
@Entity(value = "Customer", noClassnameStored = true)
@Indexes(
        @Index(value = "phoneNumber", fields = @Field("phoneNumber"))
)
@Data
public class Customer extends BaseEntity {
    private String name;

    private String phoneNumber;

    private String email;

    @Embedded
    private Address address;
}
