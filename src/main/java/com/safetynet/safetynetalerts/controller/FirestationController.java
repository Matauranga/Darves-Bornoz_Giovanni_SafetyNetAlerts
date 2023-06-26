package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.DTO.CountAdultChildWithInfosPersonDTO;
import com.safetynet.safetynetalerts.DTO.FireAlertDTO;
import com.safetynet.safetynetalerts.DTO.FloodAlertDTO;
import com.safetynet.safetynetalerts.business.FirestationService;
import com.safetynet.safetynetalerts.model.Firestation;
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
public class FirestationController {

    @Autowired
    private FirestationService firestationService;

   /* @GetMapping("/allfirestation")
    public List<Firestation> listFirestations() {
        return firestationService.getAllFirestations();
    }*/

    @PostMapping("/firestation")
    public ResponseEntity<Firestation> createFirestation(@Valid @RequestBody Firestation firestation) {
        log.info("Ask to create firestation : {}", firestation.getId());
        firestationService.createFirestation(firestation);
        log.info("Firestation created.");
        return new ResponseEntity<>(firestation, HttpStatus.CREATED);
    }

    @PutMapping("/firestation")
    public ResponseEntity<Firestation> updateFirestation(@Valid @RequestBody Firestation firestation) {
        log.info("Ask to update firestation : {}", firestation.getId());
        firestationService.updateFirestation(firestation);
        log.info("Firestation updated.");
        return new ResponseEntity<>(firestation, HttpStatus.OK);
    }


    @DeleteMapping("/firestation")
    public ResponseEntity<HttpStatus> deleteFirestation(@Valid @RequestBody Firestation firestation) {
        log.info("Ask to delete firestation : {}", firestation.getId());
        firestationService.deleteFirestation(firestation);
        log.info("Firestation deleted.");
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/phoneAlert")
    public Set<String> phoneAlertByFirestationNumber(@RequestParam Integer firestation) {
        log.info("Triggering of the phone alert for the firestation {}", firestation);
        return firestationService.getPhoneByFirestation(firestation);
    }

    @GetMapping("/firestation")
    public CountAdultChildWithInfosPersonDTO personsCoverByFirestation(@RequestParam Integer stationNumber) {
        log.info("Infos + count persons served by the firestation {}", stationNumber);
        return firestationService.personsListCoveredByFirestationAndAdultChildCount(stationNumber);
    }

    @GetMapping("/fire")
    public FireAlertDTO fireAlertAtAddress(@RequestParam String address) {
        log.info("Triggering of fire alert at {}", address);
        return firestationService.getInfosPersonsLivingAtAddressAndFirestationToCall(address);
    }

    @GetMapping("/flood/stations")
    public List<FloodAlertDTO> floodAlert(@RequestParam Set<Integer> stations) {
        log.info("Triggering of flood alert for the firestation(s) {}", stations);
        return firestationService.getHouseholdServedByFirestation(stations);
    }
}
