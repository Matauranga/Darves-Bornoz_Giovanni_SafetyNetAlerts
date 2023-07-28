package com.safetynet.safetynetalerts.DTO;

import lombok.Getter;

import java.util.List;

@Getter
public class FloodAlertDTO {
    private final String address;
    private final List<InfosPersonForFloodAlertDTO> personsLivingAtAddress;

    public FloodAlertDTO(String address, List<InfosPersonForFloodAlertDTO> personsLivingAtAddressDTO) {
        this.address = address;
        this.personsLivingAtAddress = personsLivingAtAddressDTO;
    }
}