package com.healthcarewebapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;


@Data
@Document(collection = "MedicalHistory")
public class MedicalHistory {
    @Id
    private String id;
    private Integer citizenid;
    private String fullname;
    private Integer age;
    private String sex;
    private ArrayList<String> allergy;
    private ArrayList<String> illness;
}
