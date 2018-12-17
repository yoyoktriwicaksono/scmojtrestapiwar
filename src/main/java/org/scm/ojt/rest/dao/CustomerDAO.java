package org.scm.ojt.rest.dao;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.scm.ojt.rest.config.ConfigurationManager;
import org.scm.ojt.rest.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Created by Yoyok_T on 18/10/2018.

 THIS IS AN APPROACH TO CREATE DAO BASED ON ENTITY
 WHIT THIS APPROACH WE CAN ENHANCE THE DAO EASILY FOR THE ENTITY

 */
public class CustomerDAO extends BasicDAO<Customer, ObjectId> {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerDAO.class);

    public CustomerDAO(Datastore datastore){
        super(datastore);
    }

    public ObjectId getObjectId(String id){
        try {
            return new ObjectId(id);
        } catch (Exception e) {
            LOG.info(e.getMessage());
            return null;
        }
    }
}