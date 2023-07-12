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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    final private String urlEndpointPerson = "/person";
    @Autowired
    private MockMvc mockMvc;

   /* @Test
    @Disabled//Methode supprimée
    @DisplayName("Test de la methode GET")
    public void getListPersonsTest() throws Exception {
        mockMvc.perform(get(urlEndpointPerson))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Stelzer")))
                .andExpect(content().string(containsString("Marrack")));
    }*/

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
                .andExpect(status().isOk());

    }


    @Test
    @DisplayName("test de childAlertByAddress ")
    void childAlertByAddress() throws Exception {

        mockMvc.perform(get("/childAlert?address=1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Tenley")))
                .andExpect(content().string(containsString("11")));


    }

    @Test
    @DisplayName("test de personInfosByID for specific firstname")
    void personInfosByIDWithFirstNameTest() throws Exception {
        mockMvc.perform(get("/personInfo?firstName=John&lastName=Boyd"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("John")))
                .andExpect(content().string(containsString("aznol:350mg")));

    }

    @Test
    @DisplayName("test de personInfosByID without firstname")
    void personInfosByIDWithoutFirstNameTest() throws Exception {
        mockMvc.perform(get("/personInfo?lastName=Boyd"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Jacob")))
                .andExpect(content().string(containsString("tenz@email.com")))
                .andExpect(content().string(containsString("37")));

    }


    @Test
    @DisplayName("test de flood alert")
    void emailByCityTest() throws Exception {
        mockMvc.perform(get("/communityEmail?city=Culver"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("jaboyd@email.com")))
                .andExpect(content().string(containsString("soph@email.com")))
                .andExpect(content().string(containsString("gramps@email.com")));
    }


}


