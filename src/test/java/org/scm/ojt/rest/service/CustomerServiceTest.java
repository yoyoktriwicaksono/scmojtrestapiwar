package org.scm.ojt.rest.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.scm.ojt.rest.dto.AddressDTO;
import org.scm.ojt.rest.dto.CustomerDTO;
import org.scm.ojt.rest.logic.CustomerLogic;
import org.scm.ojt.rest.services.CustomerService;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Yoyok_T on 19/10/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    private List<CustomerDTO> customerDTOList = new ArrayList<>();
    private AddressDTO addressDTO;

    private String id = "1";
    private String customerName = "customer name";
    private String phoneNumber = "022123456";
    private String email = "test@test.com";

    @Mock
    private CustomerLogic customerLogic;

    @InjectMocks
    private CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        addressDTO = new AddressDTO();
        addressDTO.setState("West Java");
        addressDTO.setStreet("Street");
        addressDTO.setCity("Bandung");
        addressDTO.setZipCode("12345");

        CustomerDTO customerDTO = new CustomerDTO(id,Long.valueOf(1),customerName,phoneNumber,email,addressDTO);
        customerDTOList.add(customerDTO);

        when(customerService.searchCustomer(anyObject(), anyObject(),anyObject())).thenReturn(customerDTOList);
        when(customerService.getById(anyObject())).thenReturn(customerDTO);
        when(customerService.createCustomer(anyObject())).thenReturn(customerDTO);
        when(customerService.updateCustomer(anyObject(), anyObject())).thenReturn(customerDTO);
        //when(customerService.deleteById(anyObject())).thenReturn(null);
        when(customerLogic.deleteCustomer(anyObject())).thenReturn(true);
    }

    @Test
    public void testSearchCustomer(){
        List<CustomerDTO> customers = customerService.searchCustomer(customerName,phoneNumber,email);

        assertNotNull(customers);
        assertEquals(customers.size(),1);
    }

    @Test
    public void testGetById(){
        CustomerDTO result = customerService.getById(id);

        assertNotNull(result);
        assertEquals(result.getId(), id);
    }

    @Test
    public void testCreateCustomer(){
        CustomerDTO newCustomerDTO = new CustomerDTO(id,Long.valueOf(1),customerName,phoneNumber,email,addressDTO);
        CustomerDTO result = customerService.createCustomer(newCustomerDTO);

        assertNotNull(result);
        assertEquals(result.getId(), id);
    }

    @Test
    public void testUpdateCustomer(){
        CustomerDTO updatedCustomerDTO = new CustomerDTO(id,Long.valueOf(1),customerName,phoneNumber,email,addressDTO);
        CustomerDTO result = customerService.updateCustomer(id, updatedCustomerDTO);

        assertNotNull(result);
        assertEquals(result.getId(), id);
    }

    @Test
    public void testDeleteById(){
        Boolean result = customerLogic.deleteCustomer(id);

        assertNotNull(result);
        assertEquals(result, true);
    }
}
