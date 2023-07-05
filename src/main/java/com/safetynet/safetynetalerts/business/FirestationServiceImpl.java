package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.DTO.*;
import com.safetynet.safetynetalerts.exceptions.FirestationNotFoundException;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.FirestationRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 *
 */
@Log4j2
@Service
public class FirestationServiceImpl implements FirestationService {
    @Autowired
    FirestationRepository firestationRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    MedicalRecordService medicalRecordService;

    /**
     * @return all firestation.
     */
    public List<Firestation> getAllFirestations() {
        return firestationRepository.getAll();
    }

    /**
     * @param firestation to create.
     */
    public void createFirestation(Firestation firestation) {

        firestationRepository.saveOrUpdate(firestation);
    }

    /**
     * @param newValueOfFirestation new value of a firestation.
     */
    public void updateFirestation(Firestation newValueOfFirestation) {

        firestationRepository.getAll()
                .stream()
                .filter(p -> newValueOfFirestation.getId().equals(p.getId()))
                .findFirst()
                .orElseThrow(() -> new FirestationNotFoundException(newValueOfFirestation.getId()))
                .update(newValueOfFirestation);
    }

    /**
     * @param firestationToDelete the firestation to delete.
     */
    public void deleteFirestation(Firestation firestationToDelete) {
        firestationRepository.delete(firestationToDelete.getId());
    }


    /* ********************************************************** URL Endpoints ********************************************************** */


    /**
     * @param firestationNumber firestation number.
     * @return List of persons(Object) cover by the firestation put in @param.
     */
    public List<Person> getPersonsCoverByFirestation(Integer firestationNumber) {

        List<Person> listPersonsCoverByStation = new ArrayList<>();
        List<Firestation> addressServedByFirestation = firestationRepository.getAll()
                .stream()
                .filter(p -> p.getStation().equals(firestationNumber))
                .toList();
        if (addressServedByFirestation.isEmpty()) {
            log.error("firestation not found");
        }
        for (Firestation firestation : addressServedByFirestation) {
            listPersonsCoverByStation.addAll(personRepository.getAll()
                    .stream()
                    .filter(p -> p.getAddress().equals(firestation.getAddress()))
                    .toList());
        }
        if (!listPersonsCoverByStation.isEmpty()) {
            log.debug("List persons cover by the firestation returned with element");
        }

        return listPersonsCoverByStation;
    }


    /**
     * @param firestationNumber firestation number.
     * @return a set of phone number(String) cover by the firestation put in @param.
     */
    public Set<String> getPhoneByFirestation(Integer firestationNumber) {
        return getPersonsCoverByFirestation(firestationNumber)
                .stream()
                .map(Person::getPhone)
                .collect(Collectors.toSet());
    }

    /**
     * @param firestationNumber firestation number.
     * @return a list of persons cover by the firestation put in @param, with their first and last name, address, phone number.This give also the adult and child count.
     */
    public CountAdultChildWithInfosPersonDTO personsListCoveredByFirestationAndAdultChildCount(Integer firestationNumber) {

        Integer adultNumber = 0;
        Integer childNumber = 0;
        List<Person> listPersonsCoverByStation = getPersonsCoverByFirestation(firestationNumber);
        List<InfosPersonCoverByFirestationDTO> infosPersonsByFirestationList = new ArrayList<>();

        for (Person person : listPersonsCoverByStation) {
            MedicalRecord medicalRecordPerson = medicalRecordService.getMedicalRecordById(person.getId());
            if (medicalRecordPerson.getAge() < 19) {
                childNumber++;
            } else {
                adultNumber++;
            }
            InfosPersonCoverByFirestationDTO infosPersonCoverByFirestationDTO
                    = new InfosPersonCoverByFirestationDTO(person);
            infosPersonsByFirestationList.add(infosPersonCoverByFirestationDTO);
        }

        return new CountAdultChildWithInfosPersonDTO(adultNumber, childNumber, infosPersonsByFirestationList);
    }

    /**
     * @param address the address where we want some infos.
     * @return a list of persons at the address put in @param, with their first and last name, phone number, age, medical records and allergies.This give also the firestation covering them.
     */
    public FireAlertDTO getInfosPersonsLivingAtAddressAndFirestationToCall(String address) {

        Integer firestationToCall = firestationRepository.getAll()
                .stream()
                .filter(p -> p.getId().equals(address))
                .findFirst()
                .orElseThrow(() -> new FirestationNotFoundException(address))
                .getStation();

        List<InfosPersonForFireAlertDTO> infosPersonsToFireAlertDTO = personRepository.getAll()
                .stream()
                .filter(p -> p.getAddress().equals(address))
                .map(person -> {
                    MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordById(person.getId());
                    return new InfosPersonForFireAlertDTO(person, medicalRecord);
                })
                .toList();

        return new FireAlertDTO(firestationToCall, infosPersonsToFireAlertDTO);
    }

    /**
     * @param stations a firestation number or a list of firestation number.
     * @return a list persons cover by the firestations put in @param, with their first and last name, phone number, age, medical records and allergies.They're sort by address.
     */
    public List<FloodAlertDTO> getHouseholdServedByFirestation(Set<Integer> stations) {

        List<String> firestationAddresses = firestationRepository.getAll()
                .stream()
                .filter(p -> stations.contains(p.getStation()))
                .map(Firestation::getAddress)
                .toList();
        if (firestationAddresses.isEmpty()) {
            log.error("No address for these firestation(s) number(s)");
        }

        List<InfosPersonForFloodAlertDTO> infosPersonsForFloodAlertByAddress = personRepository.getAll()
                .stream()
                .filter(p -> firestationAddresses.contains(p.getAddress()))
                .map(this::getInfosPersonForFloodAlertDTO)
                .toList();

        return infosPersonsForFloodAlertByAddress
                .stream()
                .collect(groupingBy(InfosPersonForFloodAlertDTO::getAddress))
                .entrySet()
                .stream()
                .map(e -> new FloodAlertDTO(e.getKey(), e.getValue()))
                .toList();
    }

    private InfosPersonForFloodAlertDTO getInfosPersonForFloodAlertDTO(Person person) {
        var medicalRecord = medicalRecordService.getMedicalRecordById(person.getId());
        return new InfosPersonForFloodAlertDTO(person, medicalRecord);
    }
}
