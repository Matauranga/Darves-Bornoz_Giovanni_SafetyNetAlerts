package com.safetynet.safetynetalerts.DTO;

import lombok.Getter;

import java.util.List;

@Getter
public class FireAlertDTO {
    private final Integer firestationServingIt;
    private final List<InfosPersonForFireAlertDTO> personsAtTheAddress;

    public FireAlertDTO(Integer firestationToCall, List<InfosPersonForFireAlertDTO> infosPersonsToFireAlertDTO) {
        this.firestationServingIt = firestationToCall;
        this.personsAtTheAddress = infosPersonsToFireAlertDTO;
    }
}
