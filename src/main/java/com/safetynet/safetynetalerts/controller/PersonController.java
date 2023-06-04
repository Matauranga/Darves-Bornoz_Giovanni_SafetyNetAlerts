package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.business.PersonService;
import com.safetynet.safetynetalerts.model.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/person")
    public List<Person> getListPersons() {
        List<Person> listPersons = personService.getAllPersons();
        return listPersons;
    }

   /* @GetMapping(value = "/person/{id}")
    public Person getPersonById(@PathVariable String id) {
        Person person = personService.getPersonById(id);
        return person;
    }*/

    @PostMapping("/person")
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
        personService.createPerson(person);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @PutMapping("/person")
    public ResponseEntity<Person> updatePerson(@Valid @RequestBody Person person) {
        personService.updatePerson(person);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @DeleteMapping("/person")
    public ResponseEntity<HttpStatus> deletePerson(@Valid @RequestBody Person person) {
        personService.deletePerson(person);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/childAlert")
    public List<ChildDTO> childAlertByAddress(@RequestParam String address) {
        List<ChildDTO> listChildPresentAtAddress = personService.getChildByAddress(address);
        //log.info
        return listChildPresentAtAddress;
    }

//    /**************************************** //TODO url a corriger ***********************************/



   /* @GetMapping("/phoneAlert")
    public List<String> phoneAlertByFirestationNumber(@RequestParam Integer firestation) {
        //log.err <1
        List<String> listPhoneNumberPersonsToAlert = personService.getPhoneByFirestation(firestation);
        return listPhoneNumberPersonsToAlert;
    }

    @GetMapping("/firestation")
    public List<Person> personsCoverByFirestation(@RequestParam Integer stationNumber) {

        List<Person> listPersonsCoverBystation = personService.getPersonsCoverByFirestation(stationNumber);
        return listPersonsCoverBystation;
    }*/


}


