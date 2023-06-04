package com.safetynet.safetynetalerts.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildDTO {
    private String firstName;
    private String lastName;
    private int age;
    private List<String> personWhitChild;

}
