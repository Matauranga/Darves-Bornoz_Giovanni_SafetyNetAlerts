package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataStorage;
import com.safetynet.safetynetalerts.repository.FirestationRepository;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class PersonServiceImplTest {
    @InjectMocks
    PersonServiceImpl personServiceImpl;
    @Mock
    DataStorage dataStorage;
    @Mock
    PersonRepository personRepository;


    @Mock
    FirestationRepository firestationRepository;
    @Mock
    MedicalRecordRepository medicalRecordRepository;
    @Mock
    MedicalRecordService medicalRecordService;

    //TODO : test optional
    @Disabled
    @Test
    @DisplayName("test de getPersonById")
    void getPersonByIdTest() {
        //Given
        List<Person> listPersons = new ArrayList<>();
        Person personTest = new Person("Chuck", "Norris", null, null, null, null, null);
        listPersons.add(personTest);

        //When
        when(dataStorage.getPersons()).thenReturn(listPersons);
        Optional<Person> response = personServiceImpl.getPersonById("Chuck-Norris");

        //Then
        assertThat(response).isEqualTo(personTest);
    }

    @Test
    @DisplayName("test de getAllPersons")
    void getAllPersonsTest() {
        //Given

        //When
        personServiceImpl.getAllPersons();

        //Then
        verify(personRepository, times(1)).getAll();

    }

    @Test
    @DisplayName("test de createPerson")
    void createPersonTest() {
        //Given
        Person personTest = new Person("Chuck", "Norris", null, null, null, null, null);

        //When
        personServiceImpl.createPerson(personTest);

        //Then
        verify(personRepository, times(1)).saveOrUpdate(any());

    }

    @Test
    @DisplayName("test de updatePerson")
    void updatePersonTest() {
        //Given
        List<Person> listPersons = new ArrayList<>();
        Person personTest = new Person("Chuck", "Norris", null, null, null, null, null);
        listPersons.add(personTest);
        Person newValueOfPersonTest = new Person("Chuck", "Norris", "Texas Rangers", null, null, null, null);

        //When
        when(personRepository.getAll()).thenReturn(listPersons);
        personServiceImpl.updatePerson(newValueOfPersonTest);

        //Then
        verify(personRepository, times(1)).getAll();
        verify(personRepository, times(1)).saveOrUpdate(any());
        assertThat(personTest.getAddress()).isEqualTo(newValueOfPersonTest.getAddress());


    }

    @Test
    @DisplayName("test de deletePerson")
    void deletePersonTest() {
        //Given
        Person personTest = new Person("Chuck", "Norris", null, null, null, null, null);

        //When
        personServiceImpl.deletePerson(personTest);

        //Then
        verify(personRepository, times(1)).delete(any());

    }


    //TODO : finir les tests
    @Test
    @DisplayName("test de getPersonsAtAddress")
    void getPersonsAtAddressTest() {
        //Given

        //When

        //Then

    }

    @Test
    @DisplayName("test de getChildByAddress")
    void getChildByAddressTest() {
        //Given

        //When

        //Then

    }

    @Test
    @DisplayName("test de getEmailByCity")
    void getEmailByCityTest() {
        //Given

        //When

        //Then

    }

    @Test
    @DisplayName("test de getInfosPersonByID")
    void getInfosPersonByIDTest() {
        //Given

        //When

        //Then

    }
}