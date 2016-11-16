package com.github.ricardobaumann.spring_crm.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * Created by ricardobaumann on 16/11/16.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentDTO {

    private Date scheduledAt;

    private Long customerId;

    private Long id;

}
