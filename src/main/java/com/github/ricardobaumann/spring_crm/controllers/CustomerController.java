package com.github.ricardobaumann.spring_crm.controllers;

import com.github.ricardobaumann.spring_crm.dtos.CustomerDTO;
import com.github.ricardobaumann.spring_crm.helpers.Converter;
import com.github.ricardobaumann.spring_crm.models.Customer;
import com.github.ricardobaumann.spring_crm.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ricardobaumann on 16/11/16.
 */
@RestController
@RequestMapping(path = "/customers",consumes = "application/json", produces = "application/json")
public class CustomerController {

    @Autowired
    private Converter converter;

    @Autowired
    private CustomerService customerService;

    @RequestMapping(method = RequestMethod.POST)
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {

        Customer customer = converter.convert(customerDTO,Customer.class);
        customer = customerService.create(customer);

        return converter.convert(customer,CustomerDTO.class);
    }

}
