package com.safetynet.safetynetalerts.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Firestation implements Entity<Firestation> {
    @NotBlank(message = "address can not be null, empty or blank")
    private String address;
    private Integer station;

    public String getId() {
        return address;
    }

    @Override
    public Firestation update(Firestation update) {
        this.station = update.getStation();
        log.debug("Firestation updated");
        return this;
    }
}
