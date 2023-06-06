package com.safetynet.safetynetalerts.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountAdultChildByFirestationWithInfosPersonDTO {
    Integer adultNumber;
    Integer childNumber;
    List<InfosPersonByFirestationDTO> InfosPersonByFirestationDTO;
}
