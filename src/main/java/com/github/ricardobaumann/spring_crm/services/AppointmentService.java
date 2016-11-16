package com.github.ricardobaumann.spring_crm.services;

import com.github.ricardobaumann.spring_crm.models.Appointment;
import com.github.ricardobaumann.spring_crm.models.Customer;
import com.github.ricardobaumann.spring_crm.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Business service class for appointments
 * Created by ricardobaumann on 16/11/16.
 */
@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment createAppointment(Appointment appointment) {
        appointment.setCreatedAt(new Date());
        return appointmentRepository.save(appointment);
    }

    public Page<Appointment> getPage(Date scheduledAt, Pageable pageable) {
        return appointmentRepository.findByScheduledAtGreaterThan(scheduledAt!=null ? scheduledAt : new Date(),pageable);
    }

    public Page<Appointment> getFutureCustomerPage(Customer customer, Pageable pageable) {
        return appointmentRepository.findByCustomerAndScheduledAtGreaterThan(customer,new Date(),pageable);
    }

    public Appointment getAppointment(Long id) {
        return appointmentRepository.findOne(id);
    }

    public Appointment updateRating(Appointment appointment, Integer rating) {
        appointment.setRating(rating);
        return appointmentRepository.save(appointment);
    }
}
