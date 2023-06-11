package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.exceptions.NotFoundException;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Todo: javadoc
@Service
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {
    private static final Logger log = LogManager.getLogger("SafetyNet Alerts");
    @Autowired
    DataStorage dataStorage;

    @Override
    public List<MedicalRecord> getAll() {
        return dataStorage.getMedicalRecords();
    }

    @Override
    public Optional<MedicalRecord> getById(String id) {
        Optional<MedicalRecord> medicalRecord = dataStorage.getMedicalRecords().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if (medicalRecord.isEmpty()) {
            log.error("Medical record not found", new NotFoundException(MedicalRecord.class, id));
        }
        return medicalRecord;
    }

    @Override
    public MedicalRecord saveOrUpdate(MedicalRecord entity) {

        var medicalRecordEntity = getById(entity.getId());

        if(medicalRecordEntity.isPresent()){
            int index =  dataStorage.getMedicalRecords().indexOf(medicalRecordEntity.get());
            dataStorage.getMedicalRecords()
                    .set(index, entity);
        }else{
            dataStorage.getMedicalRecords().add(entity);
        }

        return entity;
    }

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
