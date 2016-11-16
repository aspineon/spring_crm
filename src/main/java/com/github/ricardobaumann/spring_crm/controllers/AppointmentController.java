package com.github.ricardobaumann.spring_crm.controllers;

import com.github.ricardobaumann.spring_crm.dtos.AppointmentDTO;
import com.github.ricardobaumann.spring_crm.helpers.Converter;
import com.github.ricardobaumann.spring_crm.models.Appointment;
import com.github.ricardobaumann.spring_crm.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by ricardobaumann on 16/11/16.
 */
@RestController
@RequestMapping(path = "/appointments",consumes = "application/json", produces = "application/json")
public class AppointmentController {

    @Autowired
    private Converter converter;

    @Autowired
    private AppointmentService appointmentService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDTO postAppointment(@RequestBody AppointmentDTO appointmentDTO) {

        Appointment appointment = converter.asAppointment(appointmentDTO);

        appointment = appointmentService.createAppointment(appointment);

        return converter.convert(appointment,AppointmentDTO.class);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<AppointmentDTO> getPage(@RequestParam(name = "page",defaultValue = "0") Integer page,
                                        @RequestParam(name = "size", defaultValue = "20") Integer size,
                                        @RequestParam(name="startAt", required = false) Date startAt) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");

        return converter.asAppointmentDTOs(appointmentService.getPage(startAt,pageable));
    }


}
