package com.github.ricardobaumann.spring_crm.services;

import com.github.ricardobaumann.spring_crm.models.Customer;
import com.github.ricardobaumann.spring_crm.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ricardobaumann on 16/11/16.
 */
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createCustomerSuccessfully() throws Exception {

        String name = "test";
        Long id = 1L;

        Customer customer = new Customer();
        customer.setName(name);

        Customer savedCustomer = new Customer();
        savedCustomer.setName(name);
        savedCustomer.setId(id);
        savedCustomer.setCreatedAt(new Date());

        when(customerRepository.save(customer)).thenReturn(savedCustomer);

        customerService.create(customer);

        assertThat(savedCustomer.getId(),is(id));
        assertNotNull(savedCustomer.getCreatedAt());

        verify(customerRepository).save(customer);
    }

}