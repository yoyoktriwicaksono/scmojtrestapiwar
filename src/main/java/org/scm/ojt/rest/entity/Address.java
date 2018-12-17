package org.scm.ojt.rest.entity;

import lombok.Data;
import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by Yoyok_T on 18/10/2018.
 */
@Embedded
@Data
public class Address {
    private String street;
    private String city;
    private String state;
    private String zipCode;

}
