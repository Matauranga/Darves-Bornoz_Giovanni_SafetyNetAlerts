package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.DTO.InfosPersonDTO;
import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.FirestationRepository;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    @Autowired
    FirestationRepository firestationRepository;

    /**
     * @param id of the person search
     * @return the person search
     */
    public Optional<Person> getPersonById(String id) {
        return personRepository.getById(id);
    }

    /**
     * @return List of all persons
     */
    public List<Person> getAllPersons() {
        return personRepository.getAll();
    }

    /**
     * @param person to create
     */
    public void createPerson(Person person) {
        personRepository.saveOrUpdate(person);
    }

    /**
     * @param updatePerson
     */
    public void updatePerson(Person updatePerson) {
        Person person = personRepository.getAll()
                .stream()
                .filter(p -> updatePerson.getId().equals(p.getId()))
                .findFirst()
                .orElseThrow(() -> new PersonNotFoundException(updatePerson.getFirstName(), updatePerson.getLastName()))
                .update(updatePerson);

        personRepository.saveOrUpdate(person);

    }

    /**
     * @param personToDelete
     */

    public void deletePerson(Person personToDelete) {
        personRepository.delete(personToDelete.getId());
    }

/*
    /********************************************   TODO: changer de place certaines fonctions  *******************************************************************************/

    /**
     * @param address
     * @return
     */
    public List<Person> getPersonsAtAddress(String address) {
        List<Person> personListAtTheAddress = personRepository.getAll()
                .stream()
                .filter(p -> p.getAddress().equals(address))
                .collect(Collectors.toList());
        return personListAtTheAddress;
    }

    /**
     * @param address where is the alert
     * @return child list's present, their age and who lives with them
     */
    //TODO : Peut le faire avec du stream?
    public List<ChildDTO> getChildByAddress(String address) {
        //log.info
      /*  List<Person> listPersonsAtTheAddress = getPersonsAtAddress(address);

        return listPersonsAtTheAddress.stream()
                .map(person -> {
                            final MedicalRecord medicalRecord = medicalRecordRepository.getById(person.getId()).orElseThrow();
                            List<String> personWhithChild = new ArrayList<>();
                            if (medicalRecord.getAge() < 19) {
                                for (Person adult : listPersonsAtTheAddress) {
                                    if (!adult.getId().equals(person.getId())) {
                                        personWhithChild.add(adult.getId());
                                    }
                                }
                                return new ChildDTO(person, medicalRecord, personWhithChild);
                            }
                            return null;
                        }
                )
                .toList();

*/
        //log.info
        List<ChildDTO> childAtAddress = new ArrayList<>();
        List<Person> listPersonsAtTheAddress = getPersonsAtAddress(address);

        for (Person personAtTheAddress : listPersonsAtTheAddress) {
            //log.debug
            MedicalRecord medicalRecordPerson = medicalRecordService.getMedicalRecordById(personAtTheAddress.getId());
            if (medicalRecordPerson.getAge() < 19) {
                List<String> personWhithChild = new ArrayList<>();
                for (Person otherPersons : listPersonsAtTheAddress) {
                    if (otherPersons != personAtTheAddress) {
                        personWhithChild.add(otherPersons.getId());
                    }
                }
                ChildDTO child = new ChildDTO(personAtTheAddress, medicalRecordPerson, personWhithChild);
                childAtAddress.add(child);
            }
        }
        return childAtAddress;
    }

    /**
     * @param city
     * @return
     */
    public List<String> getEmailByCity(String city) {
        List<String> emailPersonInCity = new ArrayList<>();
        List<Person> personInCity = personRepository.getAll()
                .stream()
                .filter(p -> p.getCity().equals(city))
                .collect(Collectors.toList());
        for (Person person : personInCity) {
            emailPersonInCity.add(person.getEmail());
        }
        return emailPersonInCity;
    }

    /**
     * @param firstName
     * @param lastName
     * @return
     */
    public List<InfosPersonDTO> getInfosPersonByID(String lastName, String firstName) {

        if (StringUtils.isBlank(lastName)) {
            throw new IllegalArgumentException("LastName can't be null, empty or blank");
        }
        //Pour avoir soit une personne retournée, soit toute la famille en fonction des paramètres donnée en entrée
        Predicate<Person> predicate = p -> p.getLastName().equals(lastName);
        if (StringUtils.isNotBlank(firstName)) {
            predicate = predicate.and(p -> p.getFirstName().equals(firstName));
        }

        List<Person> persons = personRepository.getAll()
                .stream()
                .filter(predicate)
                .toList();

        return persons
                .stream()
                .map(person -> {
                    final MedicalRecord medicalRecord = medicalRecordRepository.getById(person.getId()).orElseThrow();
                    return new InfosPersonDTO(person, medicalRecord);
                })
                .toList();
    }
}
