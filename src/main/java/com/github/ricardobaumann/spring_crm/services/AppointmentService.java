package com.github.ricardobaumann.spring_crm.services;

import com.github.ricardobaumann.spring_crm.models.Appointment;
import com.github.ricardobaumann.spring_crm.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
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

}
