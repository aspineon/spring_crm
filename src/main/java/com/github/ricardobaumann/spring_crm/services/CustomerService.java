package com.github.ricardobaumann.spring_crm.services;

import com.github.ricardobaumann.spring_crm.models.Customer;
import com.github.ricardobaumann.spring_crm.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Business service class for customers
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

    public Customer getCustomer(Long id) {
        return customerRepository.findOne(id);
    }
}
