package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.DTO.AllInfosPersonDTO;
import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.business.PersonService;
import com.safetynet.safetynetalerts.model.Person;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Log4j2
@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

   /* @GetMapping("/person")
    public List<Person> getListPersons() {
        List<Person> listPersons = personService.getAllPersons();
        return listPersons;
    }*/

    @PostMapping("/person")
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
        log.info("Ask to create person : {}", person.getId());
        personService.createPerson(person);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @PutMapping("/person")
    public ResponseEntity<Person> updatePerson(@Valid @RequestBody Person person) {
        log.info("Ask to update person : {}", person.getId());
        personService.updatePerson(person);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @DeleteMapping("/person")
    public ResponseEntity<HttpStatus> deletePerson(@Valid @RequestBody Person person) {
        log.info("Ask to delete person : {}", person.getId());
        personService.deletePerson(person);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/childAlert")
    public List<ChildDTO> childAlertByAddress(@RequestParam String address) {
        return personService.childAlert(address);
    }

    @GetMapping("/personInfo")
    public List<AllInfosPersonDTO> personInfosByID(@RequestParam(required = true) String lastName,
                                                   @RequestParam(required = false) String firstName) {
        return personService.getInfosPersonByID(lastName, firstName);
    }

    @GetMapping("/communityEmail")
    public Set<String> emailByCity(@RequestParam String city) {
        return personService.getEmailByCity(city);
    }
}


