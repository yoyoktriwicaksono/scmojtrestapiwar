package org.scm.ojt.rest.dao;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.scm.ojt.rest.entity.Customer;
import org.scm.ojt.rest.entity.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Yoyok_T on 11/10/2018.
 */
public class SupplierDAO extends BasicDAO<Supplier, ObjectId> {
    private static final Logger LOG = LoggerFactory.getLogger(SupplierDAO.class);

    public SupplierDAO(Datastore datastore){
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
