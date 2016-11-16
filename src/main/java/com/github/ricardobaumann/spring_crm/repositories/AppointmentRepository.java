package com.github.ricardobaumann.spring_crm.repositories;

import com.github.ricardobaumann.spring_crm.models.Appointment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ricardobaumann on 16/11/16.
 */
@Repository
public interface AppointmentRepository extends PagingAndSortingRepository<Appointment,Long> {
}
