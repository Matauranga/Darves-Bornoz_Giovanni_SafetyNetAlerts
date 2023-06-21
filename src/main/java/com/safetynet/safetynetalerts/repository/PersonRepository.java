package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Person;


public interface PersonRepository  extends CrudRepository<Person> {

    //Optional<Person> getByFirstnameAndLastname(String firstname, String lastname);

}
