package com.github.ricardobaumann.spring_crm.services;

import com.github.ricardobaumann.spring_crm.CustomerRepository;
import com.github.ricardobaumann.spring_crm.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by ricardobaumann on 16/11/16.
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer create(Customer customer) {
        customer.setCreatedAt(new Date());
        return customerRepository.save(customer);
    }

}
