package com.healthcarewebapp;

import com.healthcarewebapp.controller.PatientController;
import com.healthcarewebapp.model.Patient;
import com.healthcarewebapp.repository.PatientRepository;
import com.healthcarewebapp.service.PatientService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @Autowired
    private PatientRepository patientRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetAllPatient() throws Exception{

        Patient patient1 = new Patient();
        patient1.setId(1l);
        patient1.setCitizenID(123);
        patient1.setFullName("Chris Wong");
        Patient patient2 = new Patient();
        patient2.setId(2l);
        patient2.setFullName("Ivan Chan");
        patient2.setCitizenID(456);

        List<Patient> patients = Arrays.asList(patient1, patient2);
        when(patientService.getAllPatient()).thenReturn(patients);
        mockMvc.perform(get("/patient"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\":1,\"fullName\":\"Chris Wong\",\"citizenID\":123},{\"id\":2,\"fullName\":\"Ivan Chan\",\"citizenID\":456}]"));
    }


    @Test
    public void testGetPatientByCitizenID() throws Exception{

        Patient patient1 = new Patient();
        patient1.setId(1l);
        patient1.setCitizenID(123);
        patient1.setFullName("Chris Wong");

        when(patientService.getSinglePatientByCitizenID(123)).thenReturn(patient1);
        mockMvc.perform(get("/patient/123"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"fullName\":\"Chris Wong\",\"citizenID\":123}"));
    }


    @Test
    public void testCreatePatient() throws Exception{

        Patient patient1 = new Patient();
        patient1.setId(1l);
        patient1.setCitizenID(123);
        patient1.setFullName("Chris Wong");

        String json = objectMapper.writeValueAsString(patient1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200));

        Patient patient = patientRepository.findByCitizenID(123);
        System.out.println(patient);

    }


    @Test
    public void testDeletePatient() throws Exception{
        Patient patient1 = new Patient();
        patient1.setId(1l);
        patient1.setCitizenID(123);
        patient1.setFullName("Chris Wong");

        String json = objectMapper.writeValueAsString(patient1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200));

        requestBuilder = MockMvcRequestBuilders
                .delete("/patient/123");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));

    }


}

