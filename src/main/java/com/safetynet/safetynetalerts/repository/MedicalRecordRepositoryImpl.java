package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 */
@Log4j2
@Service
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {
    @Autowired
    DataStorage dataStorage;

    /**
     * Retrieve all medical record
     *
     * @return a list of all medical records.
     */
    @Override
    public List<MedicalRecord> getAll() {
        return dataStorage.getMedicalRecords();
    }

    /**
     * Retrieve a medical record
     *
     * @param id the id of medical record.
     * @return a medical record if it exists.
     */
    @Override
    public Optional<MedicalRecord> getById(String id) {
        log.debug("Looking for {} medical record", id);

        return dataStorage.getMedicalRecords().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    /**
     * Create a medical record
     *
     * @param entity a medical record.
     * @return the medical record create or update.
     */
    @Override
    public MedicalRecord saveOrUpdate(MedicalRecord entity) {

        var medicalRecordEntity = getById(entity.getId());

        if (medicalRecordEntity.isPresent()) {//To be modified with dialog box asking you to confirm ?
            log.debug("Medical record already present, updated data ");
            int index = dataStorage.getMedicalRecords().indexOf(medicalRecordEntity.get());
            dataStorage.getMedicalRecords()
                    .set(index, entity);
        } else {
            dataStorage.getMedicalRecords().add(entity);
            log.debug("Medical record created");
        }

        return entity;
    }

    /**
     * Delete a medical record
     *
     * @param id the id of medical record to delete.
     */
    @Override
    public void delete(String id) {
        MedicalRecord medicalRecordToDelete = dataStorage.getMedicalRecords()
                .stream()
                .filter(p -> id.equals(p.getId()))
                .findFirst().orElse(null);
        if (medicalRecordToDelete != null) {
            dataStorage.getMedicalRecords().remove(medicalRecordToDelete);
            log.debug("Medical record deleted");
        } else {
            log.debug("Medical record not found");
        }
    }
}
