package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    public Optional<Person> getPersonById(String id) {
        return personRepository.getById(id);
    }

    public List<Person> getAllPersons() {
        return personRepository.getAll();
    }

    public void createPerson(Person person) {
        personRepository.saveOrUpdate(person);
    }

    public void updatePerson(Person updatePerson) {
        Person person = personRepository.getAll()
                .stream()
                .filter(p -> updatePerson.getId().equals(p.getId()))
                .findFirst()
                .orElseThrow(() -> new PersonNotFoundException(updatePerson.getFirstName(), updatePerson.getLastName()))
                .update(updatePerson);

        personRepository.saveOrUpdate(person);

    }

    public void deletePerson(Person personToDelete) {
        personRepository.delete(personToDelete.getId());
    }
}
