package com.safetynet.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.Person;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    final private String urlEndpointPerson = "/person";
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test de la methode GET")
    public void getListPersonsTest() throws Exception {
        mockMvc.perform(get(urlEndpointPerson))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Stelzer")))
                .andExpect(content().string(containsString("Marrack")));
    }

    @Test
    @DisplayName("Test de la methode POST")
    public void createPersonTest() throws Exception {
        Person createdPersonTest = new Person("Gio", "Agio", "123 Mayol", "Toulon", "101010", "000-111-2222", "rougeetnoir@email.com");


        mockMvc.perform(post(urlEndpointPerson)
                        .content(new ObjectMapper().writeValueAsString(createdPersonTest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Gio"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("rougeetnoir@email.com"));

    }

    @Test
    @DisplayName("Test de la methode PUT")
    public void updatePersonTest() throws Exception {
        Person updatedPersonTest = new Person("John", "Boyd", "123 Mayol", "Toulon", "303030", "000-333-444", "rougeetnoir@email.com");


        mockMvc.perform(put(urlEndpointPerson)
                        .content(new ObjectMapper().writeValueAsString(updatedPersonTest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Toulon"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("rougeetnoir@email.com"));

    }

    @Test
    @DisplayName("Test de la methode DELETE")
    public void deletePersonTest() throws Exception {
        Person personToDeleteTest = new Person("Sophia", "Zemicks", null, null, null, null, null);

        mockMvc.perform(delete(urlEndpointPerson)
                        .content(new ObjectMapper().writeValueAsString(personToDeleteTest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

    }

}


