package com.healthcarewebapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "AbnormalHealthRecord")
public class AbnormalHealthRecord {
    @Id
    private String id;
    private Integer citizenid;
    private String timestamp;
    private Float heartrateaverage;
    private Float oxygensaturationaverage;
    private Float bloodpressuresystolicaverage;
    private Float bloodpressurediastolicaverage;
    private List<Float> ecgaggregation;
    private String comment;
}
