package com.safetynet.safetynetalerts.DTO;

import lombok.Getter;

import java.util.List;

@Getter
public class CountAdultChildWithInfosPersonDTO {
    private final Integer adultNumber;
    private final Integer childNumber;
    private final List<InfosPersonCoverByFirestationDTO> infosPersonCoverByFirestationDTO;

    public CountAdultChildWithInfosPersonDTO(Integer adultNumber, Integer childNumber, List<InfosPersonCoverByFirestationDTO> infosPersonCoverByFirestationDTO) {
        this.adultNumber = adultNumber;
        this.childNumber = childNumber;
        this.infosPersonCoverByFirestationDTO = infosPersonCoverByFirestationDTO;
    }

}
