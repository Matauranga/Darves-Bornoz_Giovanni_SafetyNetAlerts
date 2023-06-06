package com.safetynet.safetynetalerts.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseholdListDTO {
    private List<InfosPersonLivingAtAddressDTO> infosPersonsLivingAtAddressDTO;

}
