package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.DTO.AllInfosPersonDTO;
import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 *
 */
@Log4j2
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    MedicalRecordService medicalRecordService;

    /**
     * Retrieve all persons
     *
     * @return List of all persons
     */
    public List<Person> getAllPersons() {
        return personRepository.getAll();
    }

    /**
     * Retrieve a person
     *
     * @param id of the person search
     * @return the person search
     */
    public Optional<Person> getPersonById(String id) {
        return personRepository.getById(id);
    }

    /**
     * Create a person
     *
     * @param person the person to create
     */
    public void createPerson(Person person) {
        personRepository.saveOrUpdate(person);
    }

    /**
     * Update the person
     *
     * @param newValuesOfPerson the new values of person
     */
    public void updatePerson(Person newValuesOfPerson) {
        personRepository.getAll()
                .stream()
                .filter(p -> newValuesOfPerson.getId().equals(p.getId()))
                .findFirst()
                .orElseThrow(() -> new PersonNotFoundException(newValuesOfPerson.getFirstName(), newValuesOfPerson.getLastName()))
                .update(newValuesOfPerson);

    }

    /**
     * Delete a person
     *
     * @param personToDelete the person to delete
     */

    public void deletePerson(Person personToDelete) {
        personRepository.delete(personToDelete.getId());
    }


    /* ********************************************************** URL Endpoints ********************************************************** */


    /**
     * Retrieve all persons at an address given
     *
     * @param address where want the persons living
     * @return a list of persons
     */
    public List<Person> getPersonsAtAddress(String address) {
        return personRepository.getAll()
                .stream()
                .filter(p -> p.getAddress().equals(address))
                .collect(toList());
    }

    /**
     * Method that give information about child(s) in a household
     *
     * @param address where is the alert
     * @return child list's present, with their first and last name, age and a list of who lives with them
     */
    public List<ChildDTO> childAlert(String address) {
        List<Person> listPersonsAtTheAddress = getPersonsAtAddress(address);
        if (listPersonsAtTheAddress.isEmpty()) {
            log.debug("Address not found");
        }
        List<MedicalRecord> medicalRecords = listPersonsAtTheAddress
                .stream()
                .map(p -> medicalRecordService.getMedicalRecordById(p.getId()))
                .toList();

        return listPersonsAtTheAddress.stream()
                .filter(p -> getMedicalRecord(medicalRecords, p).isMinor())
                .map(child -> {
                    MedicalRecord medicalRecord = getMedicalRecord(medicalRecords, child);
                    List<Person> personWithChild = listPersonsAtTheAddress
                            .stream().filter(p -> !p.getId().equals(child.getId()))
                            .toList();
                    return new ChildDTO(child, medicalRecord, personWithChild);
                }).toList();
    }

    private static MedicalRecord getMedicalRecord(List<MedicalRecord> medicalRecords, Person person) {
        return medicalRecords.stream().filter(m -> m.getId().equals(person.getId())).findFirst().orElseThrow();
    }

    /**
     * Get all person's emails in a city
     *
     * @param city the city
     * @return a set of emails
     */
    public Set<String> getEmailByCity(String city) {
        return personRepository.getAll()
                .stream()
                .filter(p -> p.getCity().equals(city))
                .map(Person::getEmail)
                .collect(Collectors.toSet());
    }

    /**
     * Method that give all information about a person or a family
     *
     * @param firstName Optional : the first name to have a specific person
     * @param lastName  Mandatory : the last name
     * @return : - With first name : a specific person
     * - Without first name : the list of persons with that name
     */
    public List<AllInfosPersonDTO> getInfosPersonByID(String lastName, String firstName) {

        if (StringUtils.isBlank(lastName)) {
            throw new IllegalArgumentException("LastName can't be null, empty or blank");
        }
        //Pour avoir soit une personne retournée, soit toute la famille en fonction des paramètres données en entrée
        Predicate<Person> predicate = p -> p.getLastName().equals(lastName);
        if (StringUtils.isNotBlank(firstName)) {
            predicate = predicate.and(p -> p.getFirstName().equals(firstName));
            log.info("Ask infos for {} {}", firstName, lastName);
        } else {
            log.info("Ask infos for {} family", lastName);
        }

        List<Person> persons = personRepository.getAll()
                .stream()
                .filter(predicate)
                .toList();

        return persons
                .stream()
                .map(person -> {
                    MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordById(person.getId());
                    return new AllInfosPersonDTO(person, medicalRecord);
                })
                .toList();
    }
}