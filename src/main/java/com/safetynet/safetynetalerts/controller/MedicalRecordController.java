package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.business.MedicalRecordService;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class MedicalRecordController {
    private final String urlEndpointMedicalRecord = "/medicalRecord";

    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping(value = urlEndpointMedicalRecord)
    public List<MedicalRecord> listMedicalRecords() throws IOException {
        List<MedicalRecord> listMedicalRecords = medicalRecordService.getAllMedicalRecords();
        return listMedicalRecords;
    }

    @PostMapping(value = urlEndpointMedicalRecord)
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.createMedicalRecord(medicalRecord);
        return new ResponseEntity<>(medicalRecord, HttpStatus.CREATED);
    }

    @PutMapping(value = urlEndpointMedicalRecord)
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.updateMedicalRecord(medicalRecord);
        return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
    }

    @DeleteMapping(value = urlEndpointMedicalRecord)
    public ResponseEntity<HttpStatus> deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.deleteMedicalRecord(medicalRecord);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}

