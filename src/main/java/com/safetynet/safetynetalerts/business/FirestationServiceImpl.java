package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.DTO.CountAdultChildByFirestationWithInfosPersonDTO;
import com.safetynet.safetynetalerts.DTO.InfosPersonByFirestationDTO;
import com.safetynet.safetynetalerts.DTO.InfosPersonLivingAtAddressDTO;
import com.safetynet.safetynetalerts.DTO.InfosPersonsLivingAtAddressAndFirestationToCallDTO;
import com.safetynet.safetynetalerts.exceptions.FirestationNotFoundException;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.FirestationRepository;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 *
 */
@Service
public class FirestationServiceImpl implements FirestationService {
    @Autowired
    FirestationRepository firestationRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    MedicalRecordRepository medicalRecordRepository;
    @Autowired
    MedicalRecordService medicalRecordService;

    /**
     * @return all firestatino
     */
    public List<Firestation> getAllFirestations() {
        return firestationRepository.getAll();
    }

    /**
     * @param firestation to create
     */
    public void createFirestation(Firestation firestation) {

        firestationRepository.saveOrUpdate(firestation);
    }

    /**
     * @param newValueOfFirestation
     */
    public void updateFirestation(Firestation newValueOfFirestation) {

        Firestation firestation = firestationRepository.getAll()
                .stream()
                .filter(p -> newValueOfFirestation.getId().equals(p.getId()))
                .findFirst()
                .orElseThrow(() -> new FirestationNotFoundException(newValueOfFirestation.getId()))
                .update(newValueOfFirestation);

        firestationRepository.saveOrUpdate(newValueOfFirestation);
    }

    /**
     * @param firestationToDelete
     */
    public void deleteFirestation(Firestation firestationToDelete) {
        firestationRepository.delete(firestationToDelete.getId());
    }


    /***** TODO : *************************************************************/

    /**
     * @param firestationNumber
     * @return
     */
    public List<Person> getPersonsCoverByFirestation(Integer firestationNumber) { //dans personService ?

        List<Person> listPersonsCoverByStation = new ArrayList<>();
        List<Firestation> addressServedByFirestation = firestationRepository.getAll()
                .stream()
                .filter(p -> p.getStation().equals(firestationNumber))
                .toList();
        //log.debug
        for (Firestation firestation : addressServedByFirestation) {
            listPersonsCoverByStation.addAll(personRepository.getAll()
                    .stream()
                    .filter(p -> p.getAddress().equals(firestation.getAddress()))
                    .toList());
        }


        return listPersonsCoverByStation;
    }


    /**
     * @param firestationNumber
     * @return
     */
    public List<String> getPhoneByFirestation(Integer firestationNumber) {
        //log.info.debug
        List<String> personPhoneNumber = new ArrayList<>();
        List<Person> listPersonsCoverByStation = getPersonsCoverByFirestation(firestationNumber);
        for (Person person : listPersonsCoverByStation) {
            personPhoneNumber.add(person.getPhone());
        }
        return personPhoneNumber;
    }

    public CountAdultChildByFirestationWithInfosPersonDTO personsListCoveredByFirestationAndAdultChildCount(Integer firestationNumber) {

        Integer adultNumber = 0;
        Integer childNumber = 0;
        List<Person> listPersonsCoverByStation = getPersonsCoverByFirestation(firestationNumber);
        List<InfosPersonByFirestationDTO> infosPersonsByFirestationList = new ArrayList<>();

        for (Person person : listPersonsCoverByStation) {
            MedicalRecord medicalRecordPerson = medicalRecordService.getMedicalRecordById(person.getId());
            //MedicalRecord medicalRecordPerson = medicalRecordRepository.getById(person.getId()).orElseThrow();
            if (medicalRecordPerson.getAge() < 19) {
                childNumber++;
            } else {
                adultNumber++;
            }
            InfosPersonByFirestationDTO infosPersonByFirestationDTO
                    = new InfosPersonByFirestationDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone());
            infosPersonsByFirestationList.add(infosPersonByFirestationDTO);
        }

        return new CountAdultChildByFirestationWithInfosPersonDTO(adultNumber, childNumber, infosPersonsByFirestationList);
    }

    /**
     * @param address
     * @return
     */
    public InfosPersonsLivingAtAddressAndFirestationToCallDTO getInfosPersonsLivingAtAddressAndFirestationToCall(String address) {

        Integer firestationToCall = firestationRepository.getAll()
                .stream()
                .filter(p -> p.getId().equals(address))
                .findFirst()
                .orElseThrow(() -> new FirestationNotFoundException(address))
                .getStation();

        List<InfosPersonLivingAtAddressDTO> listPersonsLivingAtAddress = personRepository.getAll()
                .stream()
                .filter(p -> p.getAddress().equals(address))
                .map(person -> {
                    //final MedicalRecord medicalRecord = medicalRecordRepository.getById(person.getId()).orElseThrow();
                    MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordById(person.getId());
                    return new InfosPersonLivingAtAddressDTO(person, medicalRecord);
                })
                .toList();

        return new InfosPersonsLivingAtAddressAndFirestationToCallDTO(firestationToCall, listPersonsLivingAtAddress);
    }

    /**
     * @param stations
     * @return
     */
    public List<InfosPersonLivingAtAddressDTO> getHouseholdServedByFirestation(Set<Integer> stations) {

        List<String> firestationAddresses = firestationRepository.getAll()
                .stream()
                .filter(p -> stations.contains(p.getStation()))
                .map(Firestation::getAddress)
                .toList();

        List<InfosPersonLivingAtAddressDTO> infosPersonLivingAtAddressDTO = personRepository.getAll()
                .stream()
                .filter(p -> firestationAddresses.contains(p.getAddress()))
                .map(person -> {
                    var medicalRecord = medicalRecordService.getMedicalRecordById(person.getId());
                    //final MedicalRecord medicalRecord = medicalRecordRepository.getById(person.getId()).orElseThrow();//TODO : diff avec plus haut ?
                    return new InfosPersonLivingAtAddressDTO(person, medicalRecord);
                })
                .toList();

        return infosPersonLivingAtAddressDTO.stream().sorted(Comparator.comparing(InfosPersonLivingAtAddressDTO::getAddress)).toList();
    }
}
