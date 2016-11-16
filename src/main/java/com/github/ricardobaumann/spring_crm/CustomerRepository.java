package com.github.ricardobaumann.spring_crm;

import com.github.ricardobaumann.spring_crm.models.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ricardobaumann on 16/11/16.
 */
@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer,Long> {
}
