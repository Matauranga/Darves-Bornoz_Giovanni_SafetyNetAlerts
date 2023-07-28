package com.safetynet.safetynetalerts.exceptions;

import com.safetynet.safetynetalerts.model.MedicalRecord;

public class MedicalRecordNotFoundException extends NotFoundException {
    public MedicalRecordNotFoundException(String firstName, String lastName) {
        super(MedicalRecord.class,  firstName + "-" + lastName);
    }
}
