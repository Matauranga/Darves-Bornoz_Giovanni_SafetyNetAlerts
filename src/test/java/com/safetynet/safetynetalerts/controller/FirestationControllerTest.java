package com.safetynet.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.Firestation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationControllerTest {
    private final String urlEndpointFirestation = "/firestation";
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test de la methode GET")
    public void getListFirestationTest() throws Exception {
        mockMvc.perform(get("/allfirestation"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1509 Culver St")))
                .andExpect(content().string(containsString("947 E. Rose Dr")));

    }

    @Test
    @DisplayName("Test de la mathode POST")
    public void createFirestationTest() throws Exception {
        Firestation createdFirestationTest = new Firestation("123 Mayol", 10);

        mockMvc.perform(post(urlEndpointFirestation)
                        .content(new ObjectMapper().writeValueAsString(createdFirestationTest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("123 Mayol"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.station").value(10));

    }

    @Test
    @DisplayName("Test de la methode PUT")
    public void updateFirestationTest() throws Exception {
        Firestation firestationToUpdateTest = new Firestation("1509 Culver St", 10);

        mockMvc.perform(put(urlEndpointFirestation)
                        .content(new ObjectMapper().writeValueAsString(firestationToUpdateTest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("1509 Culver St"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.station").value(10));


    }

    @Test
    @DisplayName("Test de la methode DELETE")
    public void deleteFirestationTest() throws Exception {
        Firestation firestationToDeleteTest = new Firestation("489 Manchester St", null);

        mockMvc.perform(delete(urlEndpointFirestation)
                        .content(new ObjectMapper().writeValueAsString(firestationToDeleteTest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

    }


}
