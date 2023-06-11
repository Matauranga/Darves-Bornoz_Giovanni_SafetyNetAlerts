package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.DTO.InfosPersonDTO;
import com.safetynet.safetynetalerts.business.PersonService;
import com.safetynet.safetynetalerts.model.Person;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/person")
    public List<Person> getListPersons() {
        List<Person> listPersons = personService.getAllPersons();
        return listPersons;
    }

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
        //log.info
        return personService.getChildByAddress(address);
    }

    @GetMapping("/personInfo")
    public List<InfosPersonDTO> personInfosByID(@RequestParam(required = true) String lastName,
                                                @RequestParam(required = false) String firstName
    ) {
        List<InfosPersonDTO> infosPersonDTO = personService.getInfosPersonByID(lastName, firstName);
//TODO : a voir
        log.info("Request success personInfosByID");
        return infosPersonDTO;
    }

    @GetMapping("/communityEmail")
    public List<String> emailByCity(@RequestParam String city) {
        //log.info
        return personService.getEmailByCity(city);
    }

}


