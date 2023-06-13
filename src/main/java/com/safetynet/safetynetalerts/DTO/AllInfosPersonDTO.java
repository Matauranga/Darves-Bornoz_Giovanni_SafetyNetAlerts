package com.safetynet.safetynetalerts.DTO;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import lombok.Getter;

import java.util.List;

@Getter
public class AllInfosPersonDTO {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String zip;
    private final String city;
    private final Integer age;
    private final String email;
    private final List<String> medications;
    private final List<String> allergies;

    public AllInfosPersonDTO(Person person, MedicalRecord medicalRecord) {

        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.address = person.getAddress();
        this.zip = person.getZip();
        this.city = person.getCity();
        this.age = medicalRecord.getAge();
        this.email = person.getEmail();
        this.medications = medicalRecord.getMedications();
        this.allergies = medicalRecord.getAllergies();
    }

}
