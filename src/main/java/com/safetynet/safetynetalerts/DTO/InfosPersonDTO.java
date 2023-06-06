package com.safetynet.safetynetalerts.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfosPersonDTO {
    //private String firstName;
    private String lastName;
    private Integer age;
    private String phone;
    private String email;
    private List<String> medications;
    private List<String> allergies;
    private List<String> familyMember;

}
