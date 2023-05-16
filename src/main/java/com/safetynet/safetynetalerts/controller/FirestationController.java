package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.repository.DataStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class FirestationController {
    @Autowired
    private DataStorage dataStorage;

    @GetMapping(value = "/firestation")
    public List<Firestation> listFirestations() throws IOException {

        List<Firestation> listFirestations = dataStorage.getFirestations();

        return listFirestations;
    }
}
