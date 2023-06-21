package com.safetynet.safetynetalerts.DTO;

import lombok.Getter;

import java.util.List;

@Getter
public class FireAlertDTO {
    private final Integer firestationToCall;
    private final List<InfosPersonForFireAlertDTO> infosPersonsToFireAlertDTO;

    public FireAlertDTO(Integer firestationToCall, List<InfosPersonForFireAlertDTO> infosPersonsToFireAlertDTO) {
        this.firestationToCall = firestationToCall;
        this.infosPersonsToFireAlertDTO = infosPersonsToFireAlertDTO;
    }
}
