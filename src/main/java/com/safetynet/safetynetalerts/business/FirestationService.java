package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.DTO.CountAdultChildWithInfosPersonDTO;
import com.safetynet.safetynetalerts.DTO.FireAlertDTO;
import com.safetynet.safetynetalerts.DTO.FloodAlertDTO;
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

    CountAdultChildWithInfosPersonDTO personsListCoveredByFirestationAndAdultChildCount(Integer stationNumber);

    FireAlertDTO getInfosPersonsLivingAtAddressAndFirestationToCall(String address);

    List<FloodAlertDTO> getHouseholdServedByFirestation(Set<Integer> station);
}
