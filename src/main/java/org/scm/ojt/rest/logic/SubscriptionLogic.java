package org.scm.ojt.rest.logic;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.scm.ojt.rest.dao.ConnectionManager;
import org.scm.ojt.rest.dao.CustomerDAO;
import org.scm.ojt.rest.dao.SubscriptionDAO;
import org.scm.ojt.rest.dto.CustomerDTO;
import org.scm.ojt.rest.dto.SubscriptionDTO;
import org.scm.ojt.rest.entity.Customer;
import org.scm.ojt.rest.entity.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Created by Wicaksono on 1/30/2019.
 */
public class SubscriptionLogic {
    private static final Logger LOG = LoggerFactory.getLogger(SubscriptionLogic.class);

    private final ConnectionManager connectionManager;
    private final ModelMapper modelMapper;
    private SubscriptionDAO subscriptionDAO;

    @Inject
    public SubscriptionLogic(ConnectionManager connectionManager, ModelMapper modelMapper){
        this.connectionManager = connectionManager;
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.subscriptionDAO = new SubscriptionDAO(this.connectionManager.getDatastore());
    }

    public SubscriptionDTO signUp(SubscriptionDTO subscriptionDTO){
        Subscription subscription = modelMapper.map(subscriptionDTO, Subscription.class);
        Key<Subscription> subscriptionKey = subscriptionDAO.save(subscription);
        SubscriptionDTO subscriptionResult = getById(subscriptionKey.getId().toString());
        return subscriptionResult;
    }

    public SubscriptionDTO getById(String id){
        ObjectId oid = subscriptionDAO.getObjectId(id);
        if (oid != null) {
            Subscription subscription = subscriptionDAO.get(oid);
            if (subscription != null) {
                SubscriptionDTO subscriptionDTO = modelMapper.map(subscription, SubscriptionDTO.class);
                return subscriptionDTO;
            } else {
                return null;
            }
        }
        return null;
    }

    public SubscriptionDTO getSubscriptionByEmail(String email){
        Query<Subscription> query = this.connectionManager.getDatastore().createQuery(Subscription.class);
        query.and(
                query.criteria("email").contains(email)
        );
        Subscription retrievedSubscription =  subscriptionDAO.findOne(query);
        if (retrievedSubscription != null) {
            SubscriptionDTO subscriptionDTO = modelMapper.map(retrievedSubscription, SubscriptionDTO.class);
            return subscriptionDTO    ;
        } else {
            return null;
        }
    }

}
