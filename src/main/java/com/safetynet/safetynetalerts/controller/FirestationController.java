package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.DTO.CountAdultChildByFirestationWithInfosPersonDTO;
import com.safetynet.safetynetalerts.DTO.InfosPersonLivingAtAddressDTO;
import com.safetynet.safetynetalerts.DTO.InfosPersonsLivingAtAddressAndFirestationToCallDTO;
import com.safetynet.safetynetalerts.business.FirestationService;
import com.safetynet.safetynetalerts.model.Firestation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class FirestationController {

    @Autowired
    private FirestationService firestationService;

    @GetMapping("/allfirestation") // TODO : faut-il enlevez tout les get ? R de CRUD
    public List<Firestation> listFirestations() {
        return firestationService.getAllFirestations();
    }

    @PostMapping("/firestation")
    public ResponseEntity<Firestation> createFirestation(@Valid @RequestBody Firestation firestation) {
        firestationService.createFirestation(firestation);
        return new ResponseEntity<>(firestation, HttpStatus.CREATED);
    }

    @PutMapping("/firestation")
    public ResponseEntity<Firestation> updateFirestation(@Valid @RequestBody Firestation firestation) {
        firestationService.updateFirestation(firestation);
        return new ResponseEntity<>(firestation, HttpStatus.OK);
    }


    @DeleteMapping("/firestation")
    public ResponseEntity<HttpStatus> deleteFirestation(@Valid @RequestBody Firestation firestation) {
        firestationService.deleteFirestation(firestation);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/phoneAlert")
    public List<String> phoneAlertByFirestationNumber(@RequestParam Integer firestation) {
        //log.err <1
        return firestationService.getPhoneByFirestation(firestation);
    }

    @GetMapping("/firestation")// TODO : Problème ou mauvaise endroit ????
    public CountAdultChildByFirestationWithInfosPersonDTO personsCoverByFirestation(@RequestParam Integer stationNumber) {

        return firestationService.personsListCoveredByFirestationAndAdultChildCount(stationNumber);
    }

    @GetMapping("/fire")
    public InfosPersonsLivingAtAddressAndFirestationToCallDTO fireAlertAtAddress(@RequestParam String address) {

        return firestationService.getInfosPersonsLivingAtAddressAndFirestationToCall(address);
    }

    @GetMapping("/flood/stations")
    public List<InfosPersonLivingAtAddressDTO> floodAlert(@RequestParam Set<Integer> stations) { //liste de stationS ??

        return firestationService.getHouseholdServedByFirestation(stations);
    }


}
