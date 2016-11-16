package com.github.ricardobaumann.spring_crm.services;

import com.github.ricardobaumann.spring_crm.models.Appointment;
import com.github.ricardobaumann.spring_crm.models.Customer;
import com.github.ricardobaumann.spring_crm.repositories.AppointmentRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;
import static org.hamcrest.core.Is.*;

/**
 * Created by ricardobaumann on 16/11/16.
 */
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createAppointmentSuccessfully() throws Exception {

        Appointment appointment = new Appointment();
        appointment.setCustomer(new Customer());
        appointment.setScheduledAt(new Date());

        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        appointment = appointmentService.createAppointment(appointment);

        assertNotNull(appointment.getCreatedAt());

        verify(appointmentRepository).save(appointment);

    }

}