package com.healthcarewebapp.repository;

import com.healthcarewebapp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByCitizenID(Integer citizenID);
    void deletePatientByCitizenID(Integer citizenID);
}
