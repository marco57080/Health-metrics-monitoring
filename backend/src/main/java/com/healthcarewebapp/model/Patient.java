package com.healthcarewebapp.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@Data
@Entity
@Table(name = "patient")
@EnableAutoConfiguration
public class Patient {
    @Id
    private Long id;
    private String fullName;
    private Integer citizenID;
}
