package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.exceptions.MedicalRecordNotFoundException;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.DataStorage;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    @Autowired
    DataStorage dataStorage;
    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.getAll();
    }

   /* public MedicalRecord getMedicalRecordById(String id) {

        List<MedicalRecord> medicalRecords = dataStorage.getMedicalRecords();
        return medicalRecords
                .stream()
                .filter(medicalRecord -> id.equals(medicalRecord.getId()))
                .findFirst().orElse(null);
    }*/

    public void createMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordRepository.saveOrUpdate(medicalRecord);

    }

    public void updateMedicalRecord(MedicalRecord updateMedicalRecord) {

        MedicalRecord medicalRecord = medicalRecordRepository.getAll()
                .stream()
                .filter(p -> updateMedicalRecord.getId().equals(p.getId()))
                .findFirst()
                .orElseThrow(() ->  new MedicalRecordNotFoundException(updateMedicalRecord.getFirstName(), updateMedicalRecord.getLastName()))
                .update(updateMedicalRecord);

        medicalRecordRepository.saveOrUpdate(medicalRecord);
    }

    public void deleteMedicalRecord(MedicalRecord medicalRecordToDelete) {
        medicalRecordRepository.delete(medicalRecordToDelete.getId());

    }

}
