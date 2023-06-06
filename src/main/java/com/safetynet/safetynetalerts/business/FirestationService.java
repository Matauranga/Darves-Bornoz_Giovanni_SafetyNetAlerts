package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.DTO.CountAdultChildByFirestationWithInfosPersonDTO;
import com.safetynet.safetynetalerts.DTO.HouseholdListDTO;
import com.safetynet.safetynetalerts.DTO.ListInfosPersonsLivingAtAddressAndFirestationToCallDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.Person;

import java.util.List;

public interface FirestationService {
    public List<Firestation> getAllFirestations();

    public void createFirstation(Firestation firestation);

    public void updateFirestation(Firestation updateFirestation);

    public void deleteFirestation(Firestation firestationToDelete);

    public List<Person> getPersonsCoverByFirestation(Integer firestationNumber);

    public List<String> getPhoneByFirestation(Integer firestationNumber);

    CountAdultChildByFirestationWithInfosPersonDTO personsListCoveredByFirestationAndAdultChildCount(Integer stationNumber);

    ListInfosPersonsLivingAtAddressAndFirestationToCallDTO getInfosPersonsLivingAtAddressAndFirestationToCall(String address);

    HouseholdListDTO getHouseholdServedByFirestation(Integer station);
}
