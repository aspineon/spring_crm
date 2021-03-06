package com.github.ricardobaumann.spring_crm.controllers;

import com.github.ricardobaumann.spring_crm.dtos.AppointmentDTO;
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
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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

    @Mock
    private CustomerService customerService;

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

    @Test
    public void getPage() throws Exception {
        int page = 0;
        int size = 20;
        Long id = 1L;
        Date sheduledAt = null;
        Integer rating = 10;
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");

        Appointment appointment = new Appointment();
        appointment.setId(id);

        AppointmentDTO outputAppointmentDTO = new AppointmentDTO();
        outputAppointmentDTO.setId(id);
        outputAppointmentDTO.setRating(rating);
        outputAppointmentDTO.setScheduledAt(new Date());

        List<Appointment> appointments = Arrays.asList(appointment);
        Page<Appointment> resultPage = new PageImpl<>(appointments);

        Page<AppointmentDTO> returnPage = new PageImpl<>(Arrays.asList(outputAppointmentDTO));

        when(appointmentService.getPage(sheduledAt,pageable)).thenReturn(resultPage);
        when(converter.asAppointmentDTOs(resultPage)).thenReturn(returnPage);

        mockMvc.perform(get("/appointments")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id",is(id.intValue())))
                .andExpect(jsonPath("$.content[0].scheduledAt",is(notNullValue())))
                .andExpect(jsonPath("$.content[0].rating",is(rating)));


        verify(appointmentService).getPage(sheduledAt,pageable);
        verify(converter).asAppointmentDTOs(resultPage);

    }

    @Test
    public void getCustomerPage() throws Exception {
        int page = 0;
        int size = 20;
        Long id = 1L;
        Long customerId = 20L;
        Customer customer = new Customer();
        customer.setId(customerId);

        Integer rating = 10;
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");

        Appointment appointment = new Appointment();
        appointment.setId(id);

        AppointmentDTO outputAppointmentDTO = new AppointmentDTO();
        outputAppointmentDTO.setId(id);
        outputAppointmentDTO.setRating(rating);
        outputAppointmentDTO.setScheduledAt(new Date());

        List<Appointment> appointments = Arrays.asList(appointment);
        Page<Appointment> resultPage = new PageImpl<>(appointments);

        Page<AppointmentDTO> returnPage = new PageImpl<>(Arrays.asList(outputAppointmentDTO));

        when(customerService.getCustomer(customerId)).thenReturn(customer);
        when(appointmentService.getFutureCustomerPage(customer,pageable)).thenReturn(resultPage);
        when(converter.asAppointmentDTOs(resultPage)).thenReturn(returnPage);

        mockMvc.perform(get("/appointments/customers/{customer_id}",customerId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id",is(id.intValue())))
                .andExpect(jsonPath("$.content[0].scheduledAt",is(notNullValue())))
                .andExpect(jsonPath("$.content[0].rating",is(rating)));


        verify(appointmentService).getFutureCustomerPage(customer,pageable);
        verify(converter).asAppointmentDTOs(resultPage);

    }

    @Test
    public void patchAppointmentRatingSuccessfully() throws Exception {
        Long id = 1L;
        Integer rating = 1;
        Appointment appointment = new Appointment();
        appointment.setId(id);
        appointment.setRating(rating);

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setRating(rating);

        when(appointmentService.getAppointment(id)).thenReturn(appointment);
        when(converter.convert(appointment,AppointmentDTO.class)).thenReturn(appointmentDTO);
        when(appointmentService.updateRating(appointment,rating)).thenReturn(appointment);

        mockMvc.perform(patch("/appointments/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.objectToString(appointmentDTO)))
                .andExpect(status().isOk());

        verify(appointmentService).getAppointment(id);
        verify(converter).convert(appointment,AppointmentDTO.class);
        verify(appointmentService).updateRating(appointment,rating);


    }

    @Test
    public void patchUnexistentAppointmentRating() throws Exception {
        Long id = 1L;
        Integer rating = 1;

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setRating(rating);

        when(appointmentService.getAppointment(id)).thenReturn(null);

        mockMvc.perform(patch("/appointments/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.objectToString(appointmentDTO)))
                .andExpect(status().isNotFound());

        verify(appointmentService).getAppointment(id);



    }

}