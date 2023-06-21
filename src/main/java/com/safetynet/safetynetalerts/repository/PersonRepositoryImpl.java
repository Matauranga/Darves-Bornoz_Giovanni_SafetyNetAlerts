package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.exceptions.NotFoundException;
import com.safetynet.safetynetalerts.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 */
@Service
public class PersonRepositoryImpl implements PersonRepository {
    private static final Logger log = LogManager.getLogger("SafetyNet Alerts");
    @Autowired
    private DataStorage dataStorage;

    /**
     * @return a list of all persons.
     */
    @Override
    public List<Person> getAll() {
        return dataStorage.getPersons();
    }

    /**
     * @param firstname the first name of the person.
     * @param lastname  the last name of the person.
     * @return the person searched if it exists.
     */
    /*@Override
    public Optional<Person> getByFirstnameAndLastname(String firstname, String lastname) {
        final String id = firstname + "-" + lastname;
        return this.getById(id);
    }*/

    /**
     * @param id the id f the person.
     * @return the person searched if it exists.
     */
    @Override
    public Optional<Person> getById(String id) {

        Optional<Person> person = dataStorage.getPersons().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if (person.isEmpty()) {
            log.error("Person not found", new NotFoundException(Person.class, id));
        }
        return person;
    }

    /**
     * @param entity a person.
     * @return the person create or update.
     */
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

    /**
     * @param id the id of the person to delete.
     */
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
            log.error("Person not found", new NotFoundException(Person.class, id));
        }
    }

}