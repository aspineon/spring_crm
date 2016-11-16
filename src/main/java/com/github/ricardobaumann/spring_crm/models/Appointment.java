package com.github.ricardobaumann.spring_crm.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by ricardobaumann on 16/11/16.
 */
@Entity
@Data
public class Appointment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Date scheduledAt;

    private Date createdAt;

    @ManyToOne
    @NotNull
    private Customer customer;

}
