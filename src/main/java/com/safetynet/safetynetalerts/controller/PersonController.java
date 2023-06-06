package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.DTO.InfosPersonDTO;
import com.safetynet.safetynetalerts.business.PersonService;
import com.safetynet.safetynetalerts.model.Person;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {
    private static final Logger logger = LogManager.getLogger("SafetyNet Alerts");
    @Autowired
    private PersonService personService;

    @GetMapping("/person")
    public List<Person> getListPersons() {
        List<Person> listPersons = personService.getAllPersons();
        if (!listPersons.isEmpty()) {//TODO : a voir
            logger.info("Request success getListPersons");
        }
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
        List<ChildDTO> listChildPresentAtAddress = personService.getChildByAddress(address);
        //log.info
        return listChildPresentAtAddress;
    }

    @GetMapping("/personInfo")
    public InfosPersonDTO personInfosByID(@RequestParam String firstName, String lastName) {
        InfosPersonDTO infosPersonDTO = personService.getInfosPersonByID(firstName, lastName);
//TODO : a voir
        logger.info("Request success personInfosByID");
        return infosPersonDTO;
    }

    @GetMapping("/communityEmail")
    public List<String> emailByCity(@RequestParam String city) {
        List<String> listemailByCity = personService.getEmailByCity(city);
        //log.info
        return listemailByCity;
    }

}


