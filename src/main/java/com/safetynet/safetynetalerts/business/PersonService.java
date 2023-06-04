package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.FirestationRepository;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 */

@Service
public class PersonService {
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

///*
//    /********************************************   TODO: changer de place certaines fonctions  *******************************************************************************/
//
//    /**
//     * @param address
//     * @return
//     */
//    public List<Person> getPersonsAtAddress(String address) {
//        List<Person> personListAtTheAddress = personRepository.getAll()
//                .stream()
//                .filter(p -> p.getAddress().equals(address))
//                .collect(Collectors.toList());
//        return personListAtTheAddress;
//    }
//
//    /**
//     * @param address where is the alert
//     * @return child list's present, their age and who lives with them
//     */
//    //TODO : les resultats sont bon mais la mise en page beurk !
//    public List<ChildDTO> getChildByAddress(String address) {
//        //log.info
//        List<ChildDTO> childAtAddress = new ArrayList<>();
//        List<Person> listPersonsAtTheAddress = getPersonsAtAddress(address);
//
//        for (Person personAtTheAddress : listPersonsAtTheAddress) {
//            //log.debug
//            MedicalRecord medicalRecordPerson = medicalRecordService.getMedicalRecordById(personAtTheAddress.getId());
//            if (medicalRecordPerson.getAge() < 18) {
//                List<String> personWhithChild = new ArrayList<>();
//                ChildDTO child = new ChildDTO(personAtTheAddress.getFirstName(), personAtTheAddress.getLastName(), medicalRecordPerson.getAge(), personWhithChild);
//                for (Person otherPersons : listPersonsAtTheAddress) {
//                    if (otherPersons != personAtTheAddress) {
//                        personWhithChild.add(otherPersons.getId());
//                    }
//                }
//                childAtAddress.add(child);
//            }
//        }
//        return childAtAddress;
//    }
//
//    /**
//     * @param firestationNumber
//     * @return
//     */
//    public List<Person> getPersonsCoverByFirestation(Integer firestationNumber) {
//        List<Person> listPersonsCoverByStation = new ArrayList<>();
//        List<Firestation> addressServedByFirestation = firestationRepository.getAll()
//                .stream()
//                .filter(p -> p.getStation().equals(firestationNumber))
//                .collect(Collectors.toList());
//        //log.debug
//        for (Firestation firestation : addressServedByFirestation) {
//            listPersonsCoverByStation.addAll(personRepository.getAll()
//                    .stream()
//                    .filter(p -> p.getAddress().equals(firestation.getAddress()))
//                    .collect(Collectors.toList()));
//        }
//        return listPersonsCoverByStation;
//    }
//
//
//    /**
//     * @param firestationNumber
//     * @return
//     */
//    public List<String> getPhoneByFirestation(Integer firestationNumber) {
//        //log.info.debug
//        List<String> personPhoneNumber = new ArrayList<>();
//        List<Person> listPersonsCoverByStation = getPersonsCoverByFirestation(firestationNumber);
//        for (Person person : listPersonsCoverByStation) {
//            personPhoneNumber.add(person.getPhone());
//        }
//        return personPhoneNumber;
//    }

}
