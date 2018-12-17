package org.scm.ojt.rest.logic;

import org.bson.types.ObjectId;

/**
 * Created by Yoyok_T on 19/10/2018.
 */
public abstract class BaseLogic {
    public ObjectId getObjectId(String id){
        try {
            return new ObjectId(id);
        } catch (Exception e) {
            return null;
        }
    }

}
