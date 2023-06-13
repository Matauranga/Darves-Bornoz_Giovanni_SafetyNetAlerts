package com.safetynet.safetynetalerts.DTO;

import com.safetynet.safetynetalerts.model.Person;
import lombok.Getter;

@Getter
public class InfosPersonCoverByFirestationDTO {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String phone;

    public InfosPersonCoverByFirestationDTO(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.address = person.getAddress();
        this.phone = person.getPhone();
    }


}
