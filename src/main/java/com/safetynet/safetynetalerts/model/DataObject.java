package com.safetynet.safetynetalerts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataObject {
    List<Person> persons;
    List<MedicalRecord> medicalrecords;//TODO : si problem regarder ici
    List<Firestation> firestations;

}


