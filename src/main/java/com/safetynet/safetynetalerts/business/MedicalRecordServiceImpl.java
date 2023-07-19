package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.exceptions.MedicalRecordNotFoundException;
import com.safetynet.safetynetalerts.exceptions.NotFoundException;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.DataStorage;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Log4j2
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    @Autowired
    DataStorage dataStorage;
    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    /**
     * Retrieve all medical record
     *
     * @return list of medical record
     */
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.getAll();
    }

    /**
     * Retrieve a medical record
     *
     * @param id firstname and lastname of the person whose medical record sought
     * @return the medical record search
     */
    public MedicalRecord getMedicalRecordById(String id) {
        log.debug("Try to get MedicalRecord of {}", id);
        List<MedicalRecord> medicalRecords = dataStorage.getMedicalRecords();
        return medicalRecords
                .stream()
                .filter(medicalRecord -> id.equals(medicalRecord.getId()))
                .findFirst().orElseThrow(() -> new NotFoundException(MedicalRecord.class, id));
    }

    /**
     * Create a medical record
     *
     * @param medicalRecord to create
     */
    public void createMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordRepository.saveOrUpdate(medicalRecord);

    }

    /**
     * Update the medical record
     *
     * @param newValuesOfMedicalRecord new values of medical record
     */
    public void updateMedicalRecord(MedicalRecord newValuesOfMedicalRecord) {
        medicalRecordRepository.getAll()
                .stream()
                .filter(p -> newValuesOfMedicalRecord.getId().equals(p.getId()))
                .findFirst()
                .orElseThrow(() -> new MedicalRecordNotFoundException(newValuesOfMedicalRecord.getFirstName(), newValuesOfMedicalRecord.getLastName()))
                .update(newValuesOfMedicalRecord);

    }

    /**
     * Delete a medical record
     *
     * @param medicalRecordToDelete the medical record to delete
     */
    public void deleteMedicalRecord(MedicalRecord medicalRecordToDelete) {
        medicalRecordRepository.delete(medicalRecordToDelete.getId());

    }

}
