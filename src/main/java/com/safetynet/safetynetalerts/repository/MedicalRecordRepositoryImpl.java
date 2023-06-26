package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.exceptions.NotFoundException;
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
     * @return a list of all medical records.
     */
    @Override
    public List<MedicalRecord> getAll() {
        return dataStorage.getMedicalRecords();
    }

    /**
     * @param id the id of medical record.
     * @return a medical record if it exists.
     */
    @Override
    public Optional<MedicalRecord> getById(String id) {
        log.debug("Looking for {} medical record", id);

        Optional<MedicalRecord> medicalRecord = dataStorage.getMedicalRecords().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if (medicalRecord.isEmpty()) {
            log.error("Medical record not found", new NotFoundException(MedicalRecord.class, id));
        }
        return medicalRecord;
    }

    /**
     * @param entity a medical record.
     * @return the medical record create or update.
     */
    @Override
    public MedicalRecord saveOrUpdate(MedicalRecord entity) {

        var medicalRecordEntity = getById(entity.getId());

        if (medicalRecordEntity.isPresent()) {
            int index = dataStorage.getMedicalRecords().indexOf(medicalRecordEntity.get());
            dataStorage.getMedicalRecords()
                    .set(index, entity);
        } else {
            dataStorage.getMedicalRecords().add(entity);
        }

        return entity;
    }

    /**
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
        } else {
            log.error("Medical record not found", new NotFoundException(MedicalRecord.class, id));
        }
    }
}
