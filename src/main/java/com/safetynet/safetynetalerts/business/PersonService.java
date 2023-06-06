package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.DTO.InfosPersonDTO;
import com.safetynet.safetynetalerts.model.Person;

import java.util.List;

public interface PersonService {

    List<Person> getAllPersons();

    void createPerson(Person person);

    void updatePerson(Person updatePerson);

    void deletePerson(Person personToDelete);

    List<ChildDTO> getChildByAddress(String address);

    List<String> getEmailByCity(String city);

    InfosPersonDTO getInfosPersonByID(String firstName, String lastName);
}
