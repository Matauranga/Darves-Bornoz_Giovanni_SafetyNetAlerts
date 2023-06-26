package com.safetynet.safetynetalerts.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import lombok.Getter;

import java.util.List;

@Getter
public class InfosPersonForFloodAlertDTO {

    private final String firstName;
    private final String lastName;
    private final String phone;
    private final Integer age;
    private final List<String> medications;
    private final List<String> allergies;
    @JsonIgnore
    private final String address;

    public InfosPersonForFloodAlertDTO(Person person, MedicalRecord medicalRecord) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.phone = person.getPhone();
        this.age = medicalRecord.getAge();
        this.medications = medicalRecord.getMedications();
        this.allergies = medicalRecord.getAllergies();
        this.address = person.getAddress();

    }
}




