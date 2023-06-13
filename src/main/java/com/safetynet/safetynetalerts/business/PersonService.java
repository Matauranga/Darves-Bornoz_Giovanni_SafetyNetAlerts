package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.DTO.AllInfosPersonDTO;
import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.model.Person;

import java.util.List;

public interface PersonService {

    List<Person> getAllPersons();

    void createPerson(Person person);

    void updatePerson(Person updatePerson);

    void deletePerson(Person personToDelete);

    List<ChildDTO> childAlert(String address);

    List<String> getEmailByCity(String city);

    List<AllInfosPersonDTO> getInfosPersonByID(String lastName, String firstName);
}
