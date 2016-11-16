package com.github.ricardobaumann.spring_crm.repositories;

import com.github.ricardobaumann.spring_crm.models.Appointment;
import com.github.ricardobaumann.spring_crm.models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by ricardobaumann on 16/11/16.
 */
@Repository
public interface AppointmentRepository extends PagingAndSortingRepository<Appointment,Long> {

    Page<Appointment> findByScheduledAtGreaterThan(Date scheduledAt, Pageable pageable);

    Page<Appointment> findByCustomerAndScheduledAtGreaterThan(Customer customer,Date scheduledAt, Pageable pageable);
}
