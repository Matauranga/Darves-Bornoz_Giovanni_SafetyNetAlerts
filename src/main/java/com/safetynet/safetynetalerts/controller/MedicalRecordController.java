package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.DataStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class MedicalRecordController {
    @Autowired
    private DataStorage dataStorage;

    @GetMapping(value = "/medicalRecord")
    public List<MedicalRecord> listMedicalRecords() throws IOException {

        List<MedicalRecord> listMedicalRecords = dataStorage.getMedicalRecords();

        return listMedicalRecords;
    }
}
