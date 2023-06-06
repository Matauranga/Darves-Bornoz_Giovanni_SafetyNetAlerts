package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.DTO.*;
import com.safetynet.safetynetalerts.exceptions.FirestationNotFoundException;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.FirestationRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public void createFirstation(Firestation firestation) {

        firestationRepository.saveOrUpdate(firestation);
    }

    /**
     * @param updateFirestation
     */
    public void updateFirestation(Firestation updateFirestation) {

        Firestation firestation = firestationRepository.getAll()
                .stream()
                .filter(p -> updateFirestation.getId().equals(p.getId()))
                .findFirst()
                .orElseThrow(() -> new FirestationNotFoundException(updateFirestation.getId()))
                .update(updateFirestation);

        firestationRepository.saveOrUpdate(updateFirestation);
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
                .collect(Collectors.toList());
        //log.debug
        for (Firestation firestation : addressServedByFirestation) {
            listPersonsCoverByStation.addAll(personRepository.getAll()
                    .stream()
                    .filter(p -> p.getAddress().equals(firestation.getAddress()))
                    .collect(Collectors.toList()));
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
            if (medicalRecordPerson.getAge() < 19) {
                childNumber++;
            } else {
                adultNumber++;
            }
            InfosPersonByFirestationDTO infosPersonByFirestationDTO
                    = new InfosPersonByFirestationDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone());
            infosPersonsByFirestationList.add(infosPersonByFirestationDTO);
        }
        CountAdultChildByFirestationWithInfosPersonDTO infosPersonsAndcountAdultChildCoverByFirestation = new CountAdultChildByFirestationWithInfosPersonDTO(adultNumber, childNumber, infosPersonsByFirestationList);

        return infosPersonsAndcountAdultChildCoverByFirestation;
    }

    /**
     * @param address
     * @return
     */
    public ListInfosPersonsLivingAtAddressAndFirestationToCallDTO getInfosPersonsLivingAtAddressAndFirestationToCall(String address) {

        List<InfosPersonLivingAtAddressDTO> listInfosPersonLivingAtAddressDTOS = new ArrayList<>();
        Integer firestationToCall = firestationRepository.getAll()
                .stream()
                .filter(p -> p.getId().equals(address))
                .findFirst()
                .orElseThrow(() -> new FirestationNotFoundException(address))
                .getStation();

        List<Person> listPersonsLivingAtAddress = personRepository.getAll()
                .stream()
                .filter(p -> p.getAddress().equals(address))
                .collect(Collectors.toList());

        for (Person person : listPersonsLivingAtAddress) {
            MedicalRecord medicalRecordPerson = medicalRecordService.getMedicalRecordById(person.getId());
            InfosPersonLivingAtAddressDTO infosPersonLivingAtAddressDTO = new InfosPersonLivingAtAddressDTO(person.getLastName(), person.getPhone(),
                    medicalRecordPerson.getAge(), medicalRecordPerson.getMedications(), medicalRecordPerson.getAllergies());
            listInfosPersonLivingAtAddressDTOS.add(infosPersonLivingAtAddressDTO);
        }

        ListInfosPersonsLivingAtAddressAndFirestationToCallDTO listInfosPersonsLivingAtAddressAndFirestationToCallDTO = new ListInfosPersonsLivingAtAddressAndFirestationToCallDTO(firestationToCall, listInfosPersonLivingAtAddressDTOS);
        return listInfosPersonsLivingAtAddressAndFirestationToCallDTO;
    }

    /**
     * @param station
     * @return
     */
    public HouseholdListDTO getHouseholdServedByFirestation(Integer station) {

        List<InfosPersonLivingAtAddressDTO> listInfosPersonLivingAtAddressDTOS = new ArrayList<>();

        List<Firestation> householdServedByfirestation = firestationRepository.getAll()
                .stream()
                .filter(p -> p.getStation().equals(station))
                .collect(Collectors.toList());

        for (Firestation firestation : householdServedByfirestation) {
            List<Person> listPersonsLivingAtAddress = personRepository.getAll()
                    .stream()
                    .filter(p -> p.getAddress().equals(firestation.getAddress()))
                    .collect(Collectors.toList());

            for (Person person : listPersonsLivingAtAddress) {
                MedicalRecord medicalRecordPerson = medicalRecordService.getMedicalRecordById(person.getId());
                InfosPersonLivingAtAddressDTO infosPersonLivingAtAddressDTO = new InfosPersonLivingAtAddressDTO(person.getLastName(), person.getPhone(),
                        medicalRecordPerson.getAge(), medicalRecordPerson.getMedications(), medicalRecordPerson.getAllergies());
                listInfosPersonLivingAtAddressDTOS.add(infosPersonLivingAtAddressDTO);
            }
        }
        HouseholdListDTO householdListDTO = new HouseholdListDTO(listInfosPersonLivingAtAddressDTOS);
        return householdListDTO;
    }
}
