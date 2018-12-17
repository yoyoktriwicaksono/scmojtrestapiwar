package org.scm.ojt.rest.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.scm.ojt.rest.dto.SupplierDTO;
import org.scm.ojt.rest.logic.SupplierLogic;
import org.scm.ojt.rest.services.SupplierService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yoyok_T on 22/10/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class SupplierServiceTest {

    private String id = "1";
    private String supplierID = "S001";
    private String supplierName = "Kimia Farma";
    private String supplierPhone = "022424134";

    private List<SupplierDTO> supplierDTOList = new ArrayList<>();
    @Mock
    private SupplierLogic supplierLogic;

    @InjectMocks
    private SupplierService supplierService;

    @Before
    public void setUp() throws Exception {
        SupplierDTO supplierDTO = new SupplierDTO(id,Long.valueOf(1),supplierID, supplierName, "Jl Veteran 100","Bandung", supplierPhone);
        supplierDTOList.add(supplierDTO);

        when(supplierService.searchSupplier(anyObject(),anyObject(),anyObject())).thenReturn(supplierDTOList);
        when(supplierService.getById(anyObject())).thenReturn(supplierDTO);
        when(supplierService.createSupplier(anyObject())).thenReturn(supplierDTO);
        when(supplierService.updateSupplier(anyObject(), anyObject())).thenReturn(supplierDTO);
        when(supplierLogic.deleteSupplier(anyObject())).thenReturn(true);

    }

    @Test
    public void testSearchSupplier(){
        List<SupplierDTO> suppliers = supplierService.searchSupplier(supplierID,supplierName,supplierPhone);

        assertNotNull(suppliers);
        assertEquals(suppliers.size(),1);
    }

    @Test
    public void testGetById(){
        SupplierDTO result = supplierService.getById(id);

        assertNotNull(result);
        assertEquals(result.getId(), id);
    }

    @Test
    public void testCreateSupplier(){
        SupplierDTO newSupplierDTO = new SupplierDTO(id,Long.valueOf(1),supplierID, supplierName, "Jl Veteran 100","Bandung", supplierPhone);
        SupplierDTO result = supplierService.createSupplier(newSupplierDTO);

        assertNotNull(result);
        assertEquals(result.getId(), id);
    }

    @Test
    public void testUpdateSupplier(){
        SupplierDTO updatedSupplierDTO = new SupplierDTO(id,Long.valueOf(1),supplierID, supplierName, "Jl Veteran 100","Bandung", supplierPhone);
        SupplierDTO result = supplierService.updateSupplier(id, updatedSupplierDTO);

        assertNotNull(result);
        assertEquals(result.getId(), id);
    }

    @Test
    public void testDeleteById(){
        Boolean result = supplierLogic.deleteSupplier(id);

        assertNotNull(result);
        assertEquals(result, true);
    }

}
