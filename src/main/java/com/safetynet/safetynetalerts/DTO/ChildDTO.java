package com.safetynet.safetynetalerts.DTO;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import lombok.Getter;

import java.util.List;

@Getter
public class ChildDTO {
    private final String firstName;
    private final String lastName;
    private final int age;
    private final List<PersonWithChild> personsWithChild;

    public ChildDTO(Person person, MedicalRecord medicalRecord, List<Person> personWhitChild) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.age = medicalRecord.getAge();
        this.personsWithChild = personWhitChild.stream().map(PersonWithChild::new).toList();
    }

    @Getter
    public static class PersonWithChild {
        private final String firstName;
        private final String lastName;

        PersonWithChild(Person person) {
            this.firstName = person.getFirstName();
            this.lastName = person.getLastName();
        }
    }
}
