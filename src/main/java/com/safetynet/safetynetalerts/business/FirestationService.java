package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.DTO.CountAdultChildByFirestationWithInfosPersonDTO;
import com.safetynet.safetynetalerts.DTO.InfosPersonLivingAtAddressDTO;
import com.safetynet.safetynetalerts.DTO.InfosPersonsLivingAtAddressAndFirestationToCallDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.Person;

import java.util.List;
import java.util.Set;

public interface FirestationService {
    List<Firestation> getAllFirestations();

    void createFirestation(Firestation firestation);

    void updateFirestation(Firestation updateFirestation);

    void deleteFirestation(Firestation firestationToDelete);

    List<Person> getPersonsCoverByFirestation(Integer firestationNumber);

    List<String> getPhoneByFirestation(Integer firestationNumber);

    CountAdultChildByFirestationWithInfosPersonDTO personsListCoveredByFirestationAndAdultChildCount(Integer stationNumber);

    InfosPersonsLivingAtAddressAndFirestationToCallDTO getInfosPersonsLivingAtAddressAndFirestationToCall(String address);

    List<InfosPersonLivingAtAddressDTO> getHouseholdServedByFirestation(Set<Integer> station);
}
