package org.scm.ojt.rest.dao;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mongodb.morphia.Datastore;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Yoyok_T on 19/10/2018.
 */
public class CustomerDAOTest {
//    private Datastore datastore;
//    private CustomerDAO customerDAO;

    @Before
    public void setUp() throws Exception {
//        datastore = Mockito.mock(Datastore.class);
//        customerDAO = new CustomerDAO(datastore);

    }

    @Test
    public void testFirst(){
        String test = "test";
        assertNotNull(test);
    }

}
