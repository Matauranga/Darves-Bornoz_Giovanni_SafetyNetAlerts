package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.List;

@RestController
public class PersonController {
    @Autowired
    private DataStorage dataStorage;

    @GetMapping(value = "/person")
    public List<Person> listPersons() throws IOException {

        List<Person> listPersons = dataStorage.getPersons();
        return listPersons;
    }

    @PutMapping(value = "/person")
    public void updateProduit(@RequestBody Person person) {

        //dataStorage.save(person);
    }


}
