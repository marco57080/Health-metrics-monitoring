package com.healthcarewebapp.service;

import com.healthcarewebapp.model.AbnormalHealthRecord;
import com.healthcarewebapp.model.MedicalHistory;
import com.healthcarewebapp.model.Patient;
import com.healthcarewebapp.repository.HistoryRepository;
import com.healthcarewebapp.repository.PatientRepository;
import com.healthcarewebapp.repository.RecordRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private HistoryRepository historyRepository;

    @Transactional
    public Patient save(Patient patient){
        return patientRepository.save(patient);
    }

    @Transactional
    public Patient getSinglePatientByCitizenID(Integer citizenID){
        return patientRepository.findByCitizenID(citizenID);
    }

    @Transactional
    public List<Patient> getAllPatient(){
        return patientRepository.findAll();
    }


    @Transactional
    public void deletePatient(Integer citizenID){
        patientRepository.deletePatientByCitizenID(citizenID);
    }


    public List<AbnormalHealthRecord> getAllRecordsByCitizenID(Integer citizenID){
        return recordRepository.findAllByCitizenid(citizenID);
    }
    public AbnormalHealthRecord getRecordByCitizenidAndTimestamp(Integer citizenID, String timestamp){
        return recordRepository.findByCitizenidAndTimestamp(citizenID,timestamp);
    }

    public MedicalHistory getMedicalHistoryByCitizenID(Integer citizenID){
        return historyRepository.findByCitizenid(citizenID);
    }


}