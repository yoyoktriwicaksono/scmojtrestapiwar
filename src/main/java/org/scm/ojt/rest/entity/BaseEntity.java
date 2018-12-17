package org.scm.ojt.rest.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Version;

/**
 * Created by Yoyok_T on 28/09/2018.
 */
@Data
public abstract class BaseEntity {
    @Id
    @Property("id")
    protected ObjectId id;

    @Version
    @Property("version")
    private Long version;

    public BaseEntity() {
        super();
    }

}
