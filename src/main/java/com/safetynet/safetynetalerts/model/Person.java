package com.safetynet.safetynetalerts.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Entity<Person> {

    @NotBlank(message = "firstName can not be null, empty or blank")
    private String firstName;
    @NotBlank(message = "lastName can not be null, empty or blank")
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    @Email
    private String email;

    public String getId() {
        return firstName + "-" + lastName;
    }

    public Person update(Person update) {
        this.address = update.getAddress();
        this.city = update.getCity();
        this.zip = update.getZip();
        this.phone = update.getPhone();
        this.email = update.getEmail();
        log.debug("Person updated");
        return this;
    }
}
