package com.safetynet.safetynetalerts.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfosPersonsLivingAtAddressAndFirestationToCallDTO {
    private Integer firestationToCall;
    private List<InfosPersonLivingAtAddressDTO> PersonsLivingAtAddress;

}