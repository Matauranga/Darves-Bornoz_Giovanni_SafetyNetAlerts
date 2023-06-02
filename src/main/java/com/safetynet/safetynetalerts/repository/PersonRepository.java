package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Person;

import java.util.Optional;


public interface PersonRepository  extends CrudRepository<Person> {

    Optional<Person> getByFirstnameAndLastname(String firstname, String lastname);

}
