package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.DTO.InfosPersonDTO;
import com.safetynet.safetynetalerts.exceptions.NotFoundException;
import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.FirestationRepository;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 */

@Service
public class PersonServiceImpl implements PersonService {
    private static final Logger logger = LogManager.getLogger("SafetyNet Alerts");
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
    //TODO : les resultats sont bon mais la mise en page beurk !
    public List<ChildDTO> getChildByAddress(String address) {
        //log.info
        List<ChildDTO> childAtAddress = new ArrayList<>();
        List<Person> listPersonsAtTheAddress = getPersonsAtAddress(address);

        for (Person personAtTheAddress : listPersonsAtTheAddress) {
            //log.debug
            MedicalRecord medicalRecordPerson = medicalRecordService.getMedicalRecordById(personAtTheAddress.getId());
            if (medicalRecordPerson.getAge() < 19) {
                List<String> personWhithChild = new ArrayList<>();
                ChildDTO child = new ChildDTO(personAtTheAddress.getFirstName(), personAtTheAddress.getLastName(), medicalRecordPerson.getAge(), personWhithChild);
                for (Person otherPersons : listPersonsAtTheAddress) {
                    if (otherPersons != personAtTheAddress) {
                        personWhithChild.add(otherPersons.getId());
                    }
                }
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
    public InfosPersonDTO getInfosPersonByID(String firstName, String lastName) {
        logger.debug("Entry in personInfosByID");

        List<Person> famillyMember = personRepository.getAll()
                .stream()
                .filter(p -> p.getLastName().equals(lastName))
                .collect(Collectors.toList());
        logger.debug("Succes to collect all familly members");

        List<String> familyMemberStringList = new ArrayList<>();

        for (Person person : famillyMember) {
            if (!person.getFirstName().equals(firstName)) {
                familyMemberStringList.add(person.getId());
            }
        }
        logger.debug("Succes to collect ID familly members");

        Person personSearch = personRepository.getAll()
                .stream()
                .filter(p -> p.getFirstName().equals(firstName))
                .filter(p -> p.getLastName().equals(lastName))
                .findFirst()
                .orElseThrow(() -> new PersonNotFoundException(firstName, lastName));

        logger.debug("Succes to collect Object person search");

        MedicalRecord medicalRecordPersonSearch = medicalRecordService.getMedicalRecordById(personSearch.getId());

        logger.debug("Succes to collect person search medical record");

        InfosPersonDTO personDTO = new InfosPersonDTO(personSearch.getLastName(),
                medicalRecordPersonSearch.getAge(), personSearch.getPhone(),
                personSearch.getEmail(), medicalRecordPersonSearch.getMedications(),
                medicalRecordPersonSearch.getAllergies(), familyMemberStringList);

        logger.debug("Succes to collect all infos to return");

        return personDTO;
    }

}
