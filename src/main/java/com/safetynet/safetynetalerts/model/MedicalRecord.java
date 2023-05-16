package com.safetynet.safetynetalerts.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class MedicalRecord {
    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications = new ArrayList<>();
    private List<String> allergies = new ArrayList<>();

    public String getId() {
        return firstName + "-" + lastName;
    }
}
