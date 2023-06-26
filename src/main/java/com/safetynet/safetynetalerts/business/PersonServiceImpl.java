package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.DTO.AllInfosPersonDTO;
import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

@Service
public class PersonServiceImpl implements PersonService {
    private static final Logger log = LogManager.getLogger("SafetyNet Alerts");
    @Autowired
    PersonRepository personRepository;
    @Autowired
    MedicalRecordRepository medicalRecordRepository;
    @Autowired
    MedicalRecordService medicalRecordService;

    /**
     * @return List of all persons
     */
    public List<Person> getAllPersons() {
        return personRepository.getAll();
    }

    /**
     * @param id of the person search
     * @return the person search
     */
    public Optional<Person> getPersonById(String id) {
        return personRepository.getById(id);
    }

    /**
     * @param person to create
     */
    public void createPerson(Person person) {
        personRepository.saveOrUpdate(person);
    }

    /**
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
     * @param personToDelete the person to delete
     */

    public void deletePerson(Person personToDelete) {
        personRepository.delete(personToDelete.getId());
    }


    /* ********************************************************** URL Endpoints ********************************************************** */


    /**
     * @param address where want the persons living
     * @return a list of persons(Object)
     */
    public List<Person> getPersonsAtAddress(String address) {
        return personRepository.getAll()
                .stream()
                .filter(p -> p.getAddress().equals(address))
                .collect(toList());
    }

    /**
     * @param address where is the alert
     * @return child list's present, with their first and last name, age and a list of who lives with them.
     */
    public List<ChildDTO> childAlert(String address) {
        //log.info
        List<Person> listPersonsAtTheAddress = getPersonsAtAddress(address);
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
     * @param city the city
     * @return the set of emails of people living in the city put in @param.
     */
    public Set<String> getEmailByCity(String city) {
        return personRepository.getAll()
                .stream()
                .filter(p -> p.getCity().equals(city))
                .map(Person::getEmail)
                .collect(Collectors.toSet());
    }

    /**
     * @param firstName Optional : the first name to have a specific person
     * @param lastName  Mandatory : the last name
     * @return : - With first name : a specific person
     * - Without first name : the list of persons with that name.
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