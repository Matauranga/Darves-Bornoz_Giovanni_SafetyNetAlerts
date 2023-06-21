package com.safetynet.safetynetalerts.DTO;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import lombok.Getter;

import java.util.List;

@Getter
public class InfosPersonForFloodAlertDTO {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String zip;
    private final String city;
    private final String phone;
    private final Integer age;
    private final List<String> medications;
    private final List<String> allergies;

    public InfosPersonForFloodAlertDTO(Person person, MedicalRecord medicalRecord) {
        this.firstName = person.getFirstName();
        this.address = person.getAddress();
        this.zip = person.getZip();
        this.city = person.getCity();
        this.lastName = person.getLastName();
        this.phone = person.getPhone();
        this.age = medicalRecord.getAge();
        this.medications = medicalRecord.getMedications();
        this.allergies = medicalRecord.getAllergies();
    }

}
