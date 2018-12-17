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
import org.scm.ojt.rest.dao.SupplierDAO;
import org.scm.ojt.rest.dto.CustomerDTO;
import org.scm.ojt.rest.dto.SupplierDTO;
import org.scm.ojt.rest.entity.Customer;
import org.scm.ojt.rest.entity.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yoyok_T on 11/10/2018.
 */
public class SupplierLogic extends BaseLogic {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerLogic.class);

    private final ConnectionManager connectionManager;
    private final ModelMapper modelMapper;
    private SupplierDAO supplierDAO;

    @Inject
    public SupplierLogic(ConnectionManager connectionManager, ModelMapper modelMapper){
        this.connectionManager = connectionManager;
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.supplierDAO = new SupplierDAO(this.connectionManager.getDatastore());
    }

    public SupplierDTO create(SupplierDTO supplierDTO){
        Supplier supplier = modelMapper.map(supplierDTO, Supplier.class);
        Key<Supplier> supplierKey = supplierDAO.save(supplier);
        SupplierDTO supplierResult = getById(supplierKey.getId().toString());
        return supplierResult;
    }

    public SupplierDTO getById(String id){
        ObjectId oid = supplierDAO.getObjectId(id);
        if (oid != null) {
            Supplier supplier = supplierDAO.get(oid);
            if (supplier != null) {
                SupplierDTO supplierDTO = modelMapper.map(supplier, SupplierDTO.class);
                return supplierDTO;
            } else {
                return null;
            }
        }
        return null;
    }

    public boolean deleteSupplier(String id){
        ObjectId oid = new ObjectId(id);
        //Supplier supplier = supplierDAO.get(oid);
        //WriteResult result = supplierDAO.delete(supplier);
        // or we can use deleteById, this call more efficient since it is no need to get first
        WriteResult result = supplierDAO.deleteById(oid);
        return result.wasAcknowledged();
    }

    public SupplierDTO updateSupplier(String id, SupplierDTO supplierDTO){
        ObjectId oid = supplierDAO.getObjectId(id);
        if (oid != null) {
            Query<Supplier> query = connectionManager.getDatastore().createQuery(Supplier.class);
            query.field("_id").equal(oid);
            UpdateOperations<Supplier> updateOperations = connectionManager.getDatastore().createUpdateOperations(Supplier.class);
            if (!supplierDTO.getName().isEmpty()) {
                updateOperations.set("name",supplierDTO.getName());
            }
            if (!supplierDTO.getPhone().isEmpty()) {
                updateOperations.set("phone",supplierDTO.getPhone());
            }
            if (!supplierDTO.getCity().isEmpty()) {
                updateOperations.set("city",supplierDTO.getCity());
            }
            if (!supplierDTO.getAddress().isEmpty()) {
                updateOperations.set("address",supplierDTO.getAddress());
            }
            UpdateResults updateResults = supplierDAO.update(query,updateOperations);
            LOG.info(updateResults.toString());
            // whatever the result, get based on id
            return getById(id);
        }
        return null;
    }

    public List<SupplierDTO> search(String supplierID, String supplierName, String phoneNumber){
        Query<Supplier> query = this.connectionManager.getDatastore().createQuery(Supplier.class);
        query.and(
                query.criteria("supplierID").contains(supplierID),
                query.criteria("name").contains(supplierName),
                query.criteria("phone").contains(phoneNumber)
        );
        QueryResults<Supplier> retrievedSuppliers =  supplierDAO.find(query);
        return createSupplierDTOList(retrievedSuppliers);
    }

    private List<SupplierDTO> createSupplierDTOList(QueryResults<Supplier> retrievedCustomers){
        List<SupplierDTO> suppliers = new ArrayList<SupplierDTO>();
        for (Supplier supplier : retrievedCustomers) {
            SupplierDTO supplierDTO = modelMapper.map(supplier, SupplierDTO.class);
            suppliers.add(supplierDTO);
        }
        return suppliers;
    }

}
