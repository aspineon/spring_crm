package com.github.ricardobaumann.spring_crm.controllers;

import com.github.ricardobaumann.spring_crm.dtos.AppointmentDTO;
import com.github.ricardobaumann.spring_crm.dtos.CustomerDTO;
import com.github.ricardobaumann.spring_crm.helpers.Converter;
import com.github.ricardobaumann.spring_crm.helpers.JsonHelper;
import com.github.ricardobaumann.spring_crm.models.Appointment;
import com.github.ricardobaumann.spring_crm.models.Customer;
import com.github.ricardobaumann.spring_crm.services.AppointmentService;
import com.github.ricardobaumann.spring_crm.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.ValidationException;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ricardobaumann on 16/11/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppointmentControllerTest {

    @Autowired
    private JsonHelper jsonHelper;

    @Mock
    private Converter converter;

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    @Autowired
    private ExceptionMapper exceptionMapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentController).setControllerAdvice(exceptionMapper).build();
    }

    @Test
    public void postAppointmentSuccessfully() throws Exception {

        Date scheduledAt = new Date();
        Date createdAt = new Date();
        Long customerId = 1L;
        Long id = 1L;

        AppointmentDTO inputAppointmentDTO = new AppointmentDTO();
        inputAppointmentDTO.setScheduledAt(scheduledAt);
        inputAppointmentDTO.setCustomerId(customerId);

        Appointment appointment = new Appointment();
        appointment.setCustomer(new Customer());
        appointment.setCreatedAt(createdAt);
        appointment.setScheduledAt(scheduledAt);
        appointment.setId(id);

        AppointmentDTO outputAppointmentDTO = new AppointmentDTO();
        outputAppointmentDTO.setScheduledAt(scheduledAt);
        outputAppointmentDTO.setId(1L);

        when(converter.asAppointment(inputAppointmentDTO)).thenReturn(appointment);
        when(appointmentService.createAppointment(appointment)).thenReturn(appointment);
        when(converter.convert(appointment,AppointmentDTO.class)).thenReturn(outputAppointmentDTO);

        mockMvc.perform(post("/appointments")
                .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonHelper.objectToString(inputAppointmentDTO)))
                .andExpect(status().isCreated());

        verify(converter).asAppointment(inputAppointmentDTO);
        verify(appointmentService).createAppointment(appointment);
        verify(converter).convert(appointment,AppointmentDTO.class);


    }

    @Test
    public void postUnprocessableAppointment() throws Exception {

        Date scheduledAt = new Date();
        Date createdAt = new Date();
        Long customerId = 1L;
        Long id = 1L;

        AppointmentDTO inputAppointmentDTO = new AppointmentDTO();
        inputAppointmentDTO.setScheduledAt(scheduledAt);
        inputAppointmentDTO.setCustomerId(customerId);

        Appointment appointment = new Appointment();
        appointment.setCustomer(new Customer());
        appointment.setCreatedAt(createdAt);
        appointment.setScheduledAt(scheduledAt);
        appointment.setId(id);

        AppointmentDTO outputAppointmentDTO = new AppointmentDTO();
        outputAppointmentDTO.setScheduledAt(scheduledAt);
        outputAppointmentDTO.setId(1L);

        when(converter.asAppointment(inputAppointmentDTO)).thenReturn(appointment);
        when(appointmentService.createAppointment(appointment)).thenThrow(ValidationException.class);

        mockMvc.perform(post("/appointments")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.objectToString(inputAppointmentDTO)))
                .andExpect(status().isUnprocessableEntity());

        verify(converter).asAppointment(inputAppointmentDTO);
        verify(appointmentService).createAppointment(appointment);


    }

}