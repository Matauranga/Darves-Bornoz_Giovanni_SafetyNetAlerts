package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonRepositoryImpl implements PersonRepository {

    @Autowired
    private DataStorage dataStorage;

    @Override
    public List<Person> createPerson(Person person) {
        List<Person> persons = dataStorage.getPersons();
        persons.add(person);
        return persons;
    }
}
