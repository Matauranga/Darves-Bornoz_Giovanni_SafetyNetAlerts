package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.business.PersonService;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.net.URI;
import java.util.List;

@RestController
public class PersonController {
    @Autowired
    private DataStorage dataStorage;
    @Autowired
    private PersonService personService;

    @GetMapping(value = "/person")
    public List<Person> listPersons() {

        return dataStorage.getPersons();
    }

    @PostMapping(value = "/person")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        List<Person> createdPerson = personService.createPerson(person);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(createdPerson)
                .toUri();
        return ResponseEntity.created(location).build();
    }
}


