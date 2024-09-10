package com.healthcarewebapp.controller;

import com.healthcarewebapp.model.AbnormalHealthRecord;
import com.healthcarewebapp.model.MedicalHistory;
import com.healthcarewebapp.model.Patient;
import com.healthcarewebapp.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
@ResponseBody
@CrossOrigin
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public List<Patient> getAllPatient(){
        return patientService.getAllPatient();
    }

    @GetMapping("/{citizenID}")
    public Patient getPatientByCitizenID(@PathVariable("citizenID") @Validated Integer citizenID){
        return patientService.getSinglePatientByCitizenID(citizenID);
    }

    @GetMapping("/{citizenID}/allRecords")
    public List<AbnormalHealthRecord> getPatientAllRecordsByCitizenID(@PathVariable("citizenID") @Validated Integer citizenID){
        return patientService.getAllRecordsByCitizenID(citizenID);
    }

    @GetMapping("/record/{citizenID}/{timestamp}")
    public AbnormalHealthRecord getPatientRecord(@PathVariable("citizenID") @Validated Integer citizenID, @PathVariable("timestamp") String timestamp){
        return patientService.getRecordByCitizenidAndTimestamp(citizenID,timestamp);
    }

    @GetMapping("/medicalHistory/{citizenID}")
    public MedicalHistory getMedicalHistoryByCitizenID(@PathVariable("citizenID") @Validated Integer citizenID){
        return patientService.getMedicalHistoryByCitizenID(citizenID);
    }

    @PostMapping
    public Patient createPatient(@RequestBody @Validated Patient patient){
        return patientService.save(patient);
    }

    @PutMapping
    public Patient modifyPatientInfo(@RequestBody @Validated Patient patient){
        Patient patient_ = patientService.getSinglePatientByCitizenID(patient.getCitizenID());
        patientService.save(patient);
        return patient_;
    }

    @DeleteMapping("/{citizenID}")
    public ResponseEntity<?> deletePatient(@PathVariable("citizenID") @Validated Integer citizenID){
        Patient patient_ = patientService.getSinglePatientByCitizenID(citizenID);
        patientService.deletePatient(citizenID);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
