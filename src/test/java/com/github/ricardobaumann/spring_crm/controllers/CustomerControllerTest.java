package com.github.ricardobaumann.spring_crm.controllers;

import com.github.ricardobaumann.spring_crm.dtos.CustomerDTO;
import com.github.ricardobaumann.spring_crm.helpers.Converter;
import com.github.ricardobaumann.spring_crm.helpers.JsonHelper;
import com.github.ricardobaumann.spring_crm.models.Customer;
import com.github.ricardobaumann.spring_crm.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ricardobaumann on 16/11/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerControllerTest {

    @Mock
    private Converter converter;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Autowired
    private JsonHelper jsonHelper;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void createCustomerSuccessfully() throws Exception {

        String name = "name";
        Long id = 1L;

        CustomerDTO inputCustomerDTO = new CustomerDTO();
        inputCustomerDTO.setName(name);

        Customer customer = new Customer();
        customer.setName(name);

        CustomerDTO outputCustomerDTO = new CustomerDTO();
        outputCustomerDTO.setName(name);
        outputCustomerDTO.setId(id);

        when(customerService.create(customer)).thenReturn(customer);
        when(converter.convert(customer,CustomerDTO.class)).thenReturn(outputCustomerDTO);

        when(converter.convert(inputCustomerDTO,Customer.class)).thenReturn(customer);


        mockMvc.perform(post("/customers")
                .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonHelper.objectToString(inputCustomerDTO)))
                .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id",is(id.intValue())));

        verify(customerService).create(customer);
        verify(converter).convert(customer,CustomerDTO.class);
        verify(converter).convert(inputCustomerDTO,Customer.class);

    }

}