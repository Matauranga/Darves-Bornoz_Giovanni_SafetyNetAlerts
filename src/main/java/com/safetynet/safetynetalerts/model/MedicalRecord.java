package com.safetynet.safetynetalerts.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord implements Entity<MedicalRecord> {
    private static final Logger log = LogManager.getLogger("SafetyNet Alerts");
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

    public int getAge() {
        if (birthdate == null) {
            return -1;
        }
        LocalDate birthDay = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        return Period.between(birthDay, LocalDate.now()).getYears();
    }

}
