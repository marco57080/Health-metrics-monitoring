package com.healthcarewebapp.repository;

import com.healthcarewebapp.model.MedicalHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoryRepository extends MongoRepository<MedicalHistory, String> {
    MedicalHistory findByCitizenid(Integer citizenid);
}
