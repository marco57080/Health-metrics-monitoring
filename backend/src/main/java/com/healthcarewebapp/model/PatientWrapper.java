package com.healthcarewebapp.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;


@Getter
@Setter
@Builder
public class PatientWrapper {
    private List<PatientResponse> patients;
    private Integer patient_count;
}
