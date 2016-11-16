package com.github.ricardobaumann.spring_crm.helpers;

import com.github.ricardobaumann.spring_crm.dtos.AppointmentDTO;
import com.github.ricardobaumann.spring_crm.models.Appointment;
import com.github.ricardobaumann.spring_crm.services.CustomerService;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * Helpers for model and dto converting
 * Created by ricardobaumann on 16/11/16.
 */
@Component
public class Converter {

    @Autowired
    private CustomerService customerService;

    @SneakyThrows
    public <T> T convert(Object src, Class<T> destClass) {
        T dest = destClass.newInstance();
        BeanUtils.copyProperties(src,dest);
        return dest;
    }

    public Appointment asAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = convert(appointmentDTO, Appointment.class);
        appointment.setCustomer(customerService.getCustomer(appointmentDTO.getCustomerId()));
        return appointment;
    }

    public Page<AppointmentDTO> asAppointmentDTOs(Page<Appointment> page) {
        return page.map(appointment -> Converter.this.convert(appointment,AppointmentDTO.class));
    }
}
