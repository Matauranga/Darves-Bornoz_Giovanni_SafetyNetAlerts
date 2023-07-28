package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {
    List<MedicalRecord> getAllMedicalRecords();

    void createMedicalRecord(MedicalRecord medicalRecord);

    void updateMedicalRecord(MedicalRecord updateMedicalRecord);

    void deleteMedicalRecord(MedicalRecord medicalRecordToDelete);

    MedicalRecord getMedicalRecordById(String id);
}
