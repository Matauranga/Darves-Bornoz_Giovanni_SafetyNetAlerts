package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.business.FirestationService;
import com.safetynet.safetynetalerts.model.Firestation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/firestation")
public class FirestationController {

    @Autowired
    private FirestationService firestationService;

    @GetMapping
    public List<Firestation> listFirestations() {
        List<Firestation> listFirestations = firestationService.getAllFirestations();
        return listFirestations;
    }

    @PostMapping
    public ResponseEntity<Firestation> createFirestation(@Valid @RequestBody Firestation firestation) {
        firestationService.createFirstation(firestation);
        return new ResponseEntity<>(firestation, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Firestation> updateFirestation(@Valid @RequestBody Firestation firestation) {
        firestationService.updateFirestation(firestation);
        return new ResponseEntity<>(firestation, HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteFirestation(@Valid @RequestBody Firestation firestation) {
        firestationService.deleteFirestation(firestation);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
