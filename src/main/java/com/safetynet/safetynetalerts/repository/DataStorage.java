package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

import java.io.IOException;
import java.util.List;

public interface DataStorage {

    void initDatas() throws IOException;

    List<Person> getPersons();

    List<Firestation> getFirestations();

    List<MedicalRecord> getMedicalRecords();

}



