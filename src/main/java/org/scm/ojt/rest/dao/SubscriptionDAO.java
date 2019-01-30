package org.scm.ojt.rest.dao;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.scm.ojt.rest.entity.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Wicaksono on 1/30/2019.
 */
public class SubscriptionDAO extends BasicDAO<Subscription, ObjectId> {
    private static final Logger LOG = LoggerFactory.getLogger(SubscriptionDAO.class);

    public SubscriptionDAO(Datastore datastore){
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
