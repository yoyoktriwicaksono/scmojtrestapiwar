package org.scm.ojt.rest.logic;

import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.scm.ojt.rest.dao.ConnectionManager;
import org.scm.ojt.rest.dao.CustomerDAO;
import org.scm.ojt.rest.dto.CustomerDTO;
import org.scm.ojt.rest.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yoyok_T on 18/10/2018.
 */
public class CustomerLogic {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerLogic.class);

    private final ConnectionManager connectionManager;
    private final ModelMapper modelMapper;
    private CustomerDAO customerDAO;

    @Inject
    public CustomerLogic(ConnectionManager connectionManager, ModelMapper modelMapper){
        this.connectionManager = connectionManager;
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.customerDAO = new CustomerDAO(this.connectionManager.getDatastore());
    }

    public CustomerDTO create(CustomerDTO customerDTO){
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        Key<Customer> customerKey = customerDAO.save(customer);
        CustomerDTO customerResult = getById(customerKey.getId().toString());
        return customerResult;
    }

    public CustomerDTO getById(String id){
        ObjectId oid = customerDAO.getObjectId(id);
        if (oid != null) {
            Customer customer = customerDAO.get(oid);
            if (customer != null) {
                CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
                return customerDTO;
            } else {
                return null;
            }
        }
        return null;
    }

    public boolean deleteCustomer(String id){
        ObjectId oid = new ObjectId(id);
        Customer customer = customerDAO.get(oid);
        // or we can use deleteById, this call more efficient since it is no need to get first
        WriteResult result = customerDAO.delete(customer);
        return result.wasAcknowledged();
    }

    public CustomerDTO updateCustomer(String id, CustomerDTO customerDTO){
        ObjectId oid = customerDAO.getObjectId(id);
        if (oid != null) {
            Query<Customer> query = connectionManager.getDatastore().createQuery(Customer.class);
            query.field("_id").equal(oid);
            UpdateOperations<Customer> updateOperations = connectionManager.getDatastore().createUpdateOperations(Customer.class);
            if (!customerDTO.getName().isEmpty()) {
                updateOperations.set("name",customerDTO.getName());
            }
            if (!customerDTO.getPhoneNumber().isEmpty()) {
                updateOperations.set("phoneNumber",customerDTO.getPhoneNumber());
            }
            if (!customerDTO.getEmail().isEmpty()) {
                updateOperations.set("email",customerDTO.getEmail());
            }
            if (!customerDTO.getAddress().getStreet().isEmpty()) {
                updateOperations.set("address.street",customerDTO.getAddress().getStreet());
            }
            if (!customerDTO.getAddress().getCity().isEmpty()) {
                updateOperations.set("address.city",customerDTO.getAddress().getCity());
            }
            if (!customerDTO.getAddress().getState().isEmpty()) {
                updateOperations.set("address.state",customerDTO.getAddress().getState());
            }
            if (!customerDTO.getAddress().getZipCode().isEmpty()) {
                updateOperations.set("address.zipCode",customerDTO.getAddress().getZipCode());
            }
            UpdateResults updateResults = customerDAO.update(query,updateOperations);
            //LOG.info("Updated Count = " + updateResults.getUpdatedCount());
            LOG.info(updateResults.toString());
            // whatever the result, get based on id
            return getById(id);
        }
        return null;
    }

    /*
        TODO
        1. what if the customerName is null
        2. what if the phoneNumber is null
        3. what if the email is null

        Reference:
        public List<M> search(String datefield, Date date, int width, int height, int count, int offset, UserAccount account, String query, List<String> sources) {

            List<Criteria> l = new ArrayList<>();
            Query<M> q = getDatastore().createQuery(clazz);
            if (query != null) {
                Pattern p = Pattern.compile("(.*)" + query + "(.*)", Pattern.CASE_INSENSITIVE);
                l.add(q.or(
                        q.criteria("title").equal(p),
                        q.criteria("description").equal(p)
                ));
            }
            if (account != null) {
                l.add(q.criteria("contributor").equal(account));
            }
            if (width > 0)
                l.add(q.criteria("width").greaterThanOrEq(width));
            if (height > 0)
                l.add(q.criteria("height").greaterThanOrEq(height));
            if (date != null)
                l.add(q.criteria(datefield).greaterThanOrEq(date));
            if (sources != null)
                l.add(q.criteria("source").in(sources));
            q.and(l.toArray(new Criteria[l.size()]));
            return q.order("crawlDate").offset(offset).limit(count).asList();
        }

     */
    public List<CustomerDTO> search(String customerName, String phoneNumber, String email){
        Query<Customer> query = this.connectionManager.getDatastore().createQuery(Customer.class);
        query.and(
                query.criteria("name").contains(customerName),
                query.criteria("phoneNumber").contains(phoneNumber),
                query.criteria("email").contains(email)
        );
        QueryResults<Customer> retrievedCustomers =  customerDAO.find(query);
        return createCustomerDTOList(retrievedCustomers);
    }

    private List<CustomerDTO> createCustomerDTOList(QueryResults<Customer> retrievedCustomers){
        List<CustomerDTO> customers = new ArrayList<CustomerDTO>();
        for (Customer retrievedCustomer : retrievedCustomers) {
            CustomerDTO customerDTO = modelMapper.map(retrievedCustomer, CustomerDTO.class);
            customers.add(customerDTO);
        }
        return customers;
    }

}
