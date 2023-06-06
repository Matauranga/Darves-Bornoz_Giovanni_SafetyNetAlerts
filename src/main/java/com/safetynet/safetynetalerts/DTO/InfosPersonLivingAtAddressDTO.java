package com.safetynet.safetynetalerts.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfosPersonLivingAtAddressDTO {
    // private String firstName;
    private String lastName;
    private String phone;
    private Integer age;
    private List<String> medications;
    private List<String> allergies;

}
