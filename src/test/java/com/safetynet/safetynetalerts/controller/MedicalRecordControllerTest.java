package com.safetynet.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.util.Lists.list;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerTest {
    private final String urlEndpointMedicalRecord = "/medicalRecord";
    @Autowired
    private MockMvc mockMvc;


   /* @Test
    @Disabled//Methode supprimée
    @DisplayName("Test de la methode GET")
    public void getListMedicalRecordTest() throws Exception {
        mockMvc.perform(get(urlEndpointMedicalRecord))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Stelzer")))
                .andExpect(content().string(containsString("Marrack")));

    }*/

    @Test
    @DisplayName("Test de la methode POST")
    public void createMedicalRecordTest() throws Exception {

        MedicalRecord medicalRecordTest = new MedicalRecord("Gio", "Agio", "01/01/0101",
                list("aznol:350mg", "hydrapermazol:100mg", "propane:NoLimit"),
                list("nillacilan", "ethanol"));


        mockMvc.perform(post(urlEndpointMedicalRecord)
                        .content(new ObjectMapper().writeValueAsString(medicalRecordTest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Gio"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.allergies").value(list("nillacilan", "ethanol")));

    }

    @Test
    @DisplayName("Test de la methode PUT")
    public void updateMedicalRecordTest() throws Exception {

        MedicalRecord updateMedicalRecordTest = new MedicalRecord("John", "Boyd", "01/01/0101",
                list("aznol:350mg", "hydrapermazol:100mg", "propane:NoLimit"),
                list("nillacilan", "ethanol"));


        mockMvc.perform(put(urlEndpointMedicalRecord)
                        .content(new ObjectMapper().writeValueAsString(updateMedicalRecordTest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthdate").value("01/01/0101"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.allergies").value(list("nillacilan", "ethanol")));

    }

    @Test
    @DisplayName("Test de la methode DELETE")
    public void deleteMedicalRecordTest() throws Exception {
        MedicalRecord medicalRecordToDeleteTest = new MedicalRecord("Sophia", "Zemicks", null,
                list(),
                list());

        mockMvc.perform(delete(urlEndpointMedicalRecord)
                        .content(new ObjectMapper().writeValueAsString(medicalRecordToDeleteTest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

}
