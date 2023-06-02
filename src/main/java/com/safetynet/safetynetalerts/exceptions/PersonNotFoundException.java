package com.safetynet.safetynetalerts.exceptions;

import com.safetynet.safetynetalerts.model.Person;

public class PersonNotFoundException extends NotFoundException {

    public PersonNotFoundException(String firstName, String lastName){
        super(Person.class, firstName + "-" + lastName);
    }
}

