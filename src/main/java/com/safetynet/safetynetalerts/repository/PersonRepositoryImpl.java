package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.exceptions.NotFoundException;
import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonRepositoryImpl implements PersonRepository {

    private static final Logger logger = LogManager.getLogger("SafetyNet Alerts");

    @Autowired
    DataStorage dataStorage;

    @Override
    public List<Person> getAll() {
        return dataStorage.getPersons();
    }

    @Override
    public Optional<Person> getByFirstnameAndLastname(String firstname, String lastname) {
        final String id = firstname + "-" + lastname;
        return this.getById(id);
    }

    @Override
    public Optional<Person> getById(String id) {

        Optional<Person> person = dataStorage.getPersons().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if (person.isEmpty()) {
            logger.error("Person not found", new NotFoundException(Person.class, id));
        }
        return person;
    }

    @Override
    public Person saveOrUpdate(Person entity) {
        var personEntity = getById(entity.getId());
        if (personEntity.isPresent()) {
            int index = dataStorage.getPersons().indexOf(personEntity.get());
            dataStorage.getPersons()
                    .set(index, entity);
        } else {
            dataStorage.getPersons().add(entity);
        }
        return entity;
    }

    @Override
    public void delete(String id) {
        Person personToDelete = dataStorage.getPersons()
                .stream()
                .filter(p -> id.equals(p.getId()))
                .findFirst()
                .orElse(null);
        if (personToDelete != null) {
            dataStorage.getPersons().remove(personToDelete);
        } else {
            logger.error("Person not found", new NotFoundException(Person.class, id));
        }
    }

}

/*
    try {
            } catch (Exception e) {
            throw new NotFoundException(Person.class, id);
             throw new PersonNotFoundException(entity.getFirstName(), entity.getLastName());
        }

 */