package com.healthcarewebapp.model;

import lombok.Data;

@Data
public class HealthRecord {
    private Integer citizenid;
    private String timestamp;
    private Float heart_rate;
    private Float spo2;
    private Float blood_pressure_systolic;
    private Float blood_pressure_diastolic;
    private Double ECG;
    private Integer record_type;
}
