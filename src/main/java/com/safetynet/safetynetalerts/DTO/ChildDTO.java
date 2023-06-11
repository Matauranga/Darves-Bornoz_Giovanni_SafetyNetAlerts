package com.safetynet.safetynetalerts.DTO;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildDTO {
    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private List<String> personWhitChild;

    public ChildDTO(Person person, MedicalRecord medicalRecord, List<String> personWhitChild) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.address = person.getAddress() + ", " + person.getZip() + ", " + person.getCity();
        this.age = medicalRecord.getAge();
        this.personWhitChild = personWhitChild;

    }

}
