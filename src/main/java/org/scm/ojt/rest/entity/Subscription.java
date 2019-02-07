package org.scm.ojt.rest.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mongodb.morphia.annotations.*;

/**
 * Created by Wicaksono on 1/30/2019.
 */
@Entity(value = "Subscription", noClassnameStored = true)
@Indexes(
        @Index(value = "email", fields = @Field("email"))
)
@Data
@EqualsAndHashCode(callSuper = false)
public class Subscription extends BaseEntity {
    @Property("email")
    private String email;

    @Property("name")
    private String name;

    @Property("password")
    private String password;

    @Property("token")
    private String token;

    @Property("role")
    private String role;
}
