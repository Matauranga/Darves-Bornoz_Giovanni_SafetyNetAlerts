package com.safetynet.safetynetalerts.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord implements Entity<MedicalRecord> {
    @NotBlank(message = "firstName can not be null, empty or blank")
    private String firstName;
    @NotBlank(message = "lastName can not be null, empty or blank")
    private String lastName;
    private String birthdate;
    private List<String> medications = new ArrayList<>();
    private List<String> allergies = new ArrayList<>();

    public String getId() {
        return firstName + "-" + lastName;
    }

    public MedicalRecord update(MedicalRecord update) {
        this.birthdate = update.getBirthdate();
        this.medications = update.getMedications();
        this.allergies = update.getAllergies();
        return this;
    }

}
