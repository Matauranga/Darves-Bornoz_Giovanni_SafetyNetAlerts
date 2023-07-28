package com.safetynet.safetynetalerts.model;

import lombok.Data;

import java.util.List;

@Data
public class DataObject {
    List<Person> persons;
    List<MedicalRecord> medicalrecords;
    List<Firestation> firestations;

}


