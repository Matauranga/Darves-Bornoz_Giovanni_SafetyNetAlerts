package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.DTO.CountAdultChildByFirestationWithInfosPersonDTO;
import com.safetynet.safetynetalerts.DTO.HouseholdListDTO;
import com.safetynet.safetynetalerts.DTO.ListInfosPersonsLivingAtAddressAndFirestationToCallDTO;
import com.safetynet.safetynetalerts.business.FirestationService;
import com.safetynet.safetynetalerts.model.Firestation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InterfaceAddress;
import java.util.List;

@RestController
public class FirestationController {

    @Autowired
    private FirestationService firestationService;

    @GetMapping("/allfirestation") // TODO : faut'il enlevez tout les get ? R
    public List<Firestation> listFirestations() {
        List<Firestation> listFirestations = firestationService.getAllFirestations();
        return listFirestations;
    }

    @PostMapping("/firestation")
    public ResponseEntity<Firestation> createFirestation(@Valid @RequestBody Firestation firestation) {
        firestationService.createFirstation(firestation);
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
        List<String> listPhoneNumberPersonsToAlert = firestationService.getPhoneByFirestation(firestation);
        return listPhoneNumberPersonsToAlert;
    }

    @GetMapping("/firestation")// TODO : Probleme ou mauvaise endroit ????
    public CountAdultChildByFirestationWithInfosPersonDTO personsCoverByFirestation(@RequestParam Integer stationNumber) {

        CountAdultChildByFirestationWithInfosPersonDTO listPersonsCoverBystation = firestationService.personsListCoveredByFirestationAndAdultChildCount(stationNumber);
        return listPersonsCoverBystation;
    }

    @GetMapping("/fire")
    public ListInfosPersonsLivingAtAddressAndFirestationToCallDTO fireAlertAtAddress(@RequestParam String address) {

        ListInfosPersonsLivingAtAddressAndFirestationToCallDTO listInfosPersonsLivingAtAddressAndFirestationToCallDTO = firestationService.getInfosPersonsLivingAtAddressAndFirestationToCall(address);

        return listInfosPersonsLivingAtAddressAndFirestationToCallDTO;
    }

    @GetMapping("/flood/stations")
    public HouseholdListDTO floodAlert(@RequestParam Integer stations) { //liste de stationS ??

        HouseholdListDTO householdListDTO = firestationService.getHouseholdServedByFirestation(stations);
        return householdListDTO;
    }


}
