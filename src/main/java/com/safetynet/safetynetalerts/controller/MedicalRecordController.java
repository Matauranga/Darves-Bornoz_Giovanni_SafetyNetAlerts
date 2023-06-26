package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.business.MedicalRecordService;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    /*@GetMapping()
    public List<MedicalRecord> listMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }*/

    @PostMapping()
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.info("Ask to create medical record : {}", medicalRecord.getId());
        medicalRecordService.createMedicalRecord(medicalRecord);
        return new ResponseEntity<>(medicalRecord, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.info("Ask to modify medical record : {}", medicalRecord.getId());
        medicalRecordService.updateMedicalRecord(medicalRecord);
        return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.info("Ask to delete medical record : {}", medicalRecord.getId());
        medicalRecordService.deleteMedicalRecord(medicalRecord);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}

