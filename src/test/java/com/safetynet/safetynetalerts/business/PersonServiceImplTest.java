package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.DTO.AllInfosPersonDTO;
import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.list;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class PersonServiceImplTest {
    @InjectMocks
    PersonServiceImpl personServiceImpl;

    @Mock
    PersonRepository personRepository;
    @Mock
    MedicalRecordService medicalRecordService;

    private List<Person> initListPersons() {
        Person person1 = new Person("Gio", "gio", "123 Mayol", "Toulon", "101010", "000-111-2222", "rougeetnoir@email.com");
        //child
        Person person2 = new Person("AGio", "gio", "123 Mayol", "Toulon", "101010", "000-111-3333", "noiretrouge@email.com");
        Person person3 = new Person("BGio", "gio", "123 Mayol", "Toulon", "101010", "000-111-4444", "redandblack@email.com");

        List<Person> persons = List.of(person1, person2, person3);

        when(personRepository.getAll()).thenReturn(persons);

        return persons;
    }

    private List<MedicalRecord> initListMedicalRecords() {
        MedicalRecord medicalRecord1 = new MedicalRecord("Gio", "gio", "01/01/2000", list("propane:NoLimit"), list("ethanol"));
        MedicalRecord medicalRecord2 = new MedicalRecord("AGio", "gio", "01/01/2020", list("butane:forSmile"), list("bioethanol"));
        MedicalRecord medicalRecord3 = new MedicalRecord("BGio", "gio", "01/01/1999", list("uranium:toDie"), list("methanol"));

        List<MedicalRecord> medicalRecords = List.of(medicalRecord1, medicalRecord2, medicalRecord3);

        when(medicalRecordService.getAllMedicalRecords()).thenReturn(medicalRecords);

        return medicalRecords;
    }

    private List<Firestation> initListFirestations() {

        return List.of(new Firestation("123 Mayol", 45));
    }

    private void initExistingPersonsMedicalRecordsAndFirestation() {
        initListPersons();
        initListMedicalRecords();
        initListFirestations();
    }

    @Test
    @DisplayName("test de getAllPersons")
    void getAllPersonsTest() {
        //Given

        //When we search all persons
        personServiceImpl.getAllPersons();

        //Then we verify if this have works correctly
        verify(personRepository, times(1)).getAll();
    }

    @Test
    @DisplayName("test de getPersonById")
    void getPersonByIdTest() {
        //Given an initial list of persons
        Person personTest = new Person("Chuck", "Norris", null, null, null, null, null);

        //When we search this person
        when(personRepository.getById(personTest.getId())).thenReturn(Optional.of(personTest));
        Optional<Person> response = personServiceImpl.getPersonById(personTest.getId());

        //Then we verify if we have the good person
        assertTrue(response.isPresent());
        assertEquals(personTest, response.get());
    }

    @Test
    @DisplayName("test de createPerson")
    void createPersonTest() {
        //Given a new person
        Person personTest = new Person("Chuck", "Norris", null, null, null, null, null);

        //When we create it
        personServiceImpl.createPerson(personTest);

        //Then we verify if this have works correctly
        verify(personRepository, times(1)).saveOrUpdate(any());

    }

    @Test
    @DisplayName("test de updatePerson")
    void updatePersonTest() {
        //Given a person and new value for this person
        List<Person> listPersons = new ArrayList<>();
        Person personTest = new Person("Chuck", "Norris", null, null, null, null, null);
        listPersons.add(personTest);
        Person newValueOfPersonTest = new Person("Chuck", "Norris", "Texas Rangers", null, null, null, null);

        //When we update the person
        when(personRepository.getAll()).thenReturn(listPersons);
        personServiceImpl.updatePerson(newValueOfPersonTest);

        //Then we verify the new values of person
        verify(personRepository, times(1)).getAll();
        assertThat(personTest.getAddress()).isEqualTo(newValueOfPersonTest.getAddress());
    }

    @Test
    @DisplayName("test de updatePerson throwing PersonNotFoundException")
    void updatePersonThrowingPersonNotFoundExceptionTest() {
        //Given an initial medical record
        Person person = new Person("Jean", "DeLaFontaine", null, null, null, null, null);

        //When we send the request
        try {
            personServiceImpl.updatePerson(person);
        } catch (PersonNotFoundException PerNotFound) {
            //Then we verify the message passed
            assertThat(PerNotFound.getMessage()).contains("Jean-DeLaFontaine");
        }
    }

    @Test
    @DisplayName("test de deletePerson")
    void deletePersonTest() {
        //Given an initial person
        Person personTest = new Person("Chuck", "Norris", null, null, null, null, null);

        //When we delete it
        personServiceImpl.deletePerson(personTest);

        //Then we verify if this have works correctly
        verify(personRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("test de getPersonsAtAddress")
    void getPersonsAtAddressTest() {
        //Given initial list of person and an expected person
        initListPersons();
        Person expectedPerson = new Person("Gio", "gio", "123 Mayol", "Toulon", "101010", "000-111-2222", "rougeetnoir@email.com");

        //When we search persons at an address
        List<Person> response = personServiceImpl.getPersonsAtAddress("123 Mayol");

        //Then we verify if this have works correctly and
        verify(personRepository, times(1)).getAll();
        assertThat(response).contains(expectedPerson);
    }

    @Test
    @DisplayName("test de getChildByAddress")
    void getChildByAddressTest() {
        //Given initials lists of persons, medical records and firestation
        List<Person> persons = initListPersons();
        List<MedicalRecord> medicalRecords = initListMedicalRecords();
        initExistingPersonsMedicalRecordsAndFirestation();
        Person expectedChild = new Person("AGio", "gio", null, null, null, null, null);

        //When search child at an address
        when(medicalRecordService.getMedicalRecordById(persons.get(0).getId())).thenReturn(medicalRecords.get(0));
        when(medicalRecordService.getMedicalRecordById(persons.get(1).getId())).thenReturn(medicalRecords.get(1));
        when(medicalRecordService.getMedicalRecordById(persons.get(2).getId())).thenReturn(medicalRecords.get(2));
        List<ChildDTO> response = personServiceImpl.childAlert("123 Mayol");

        //Then we verify if the response contains the child expected
        assertThat(response.get(0).getFirstName()).isEqualTo(expectedChild.getFirstName());
    }

    @Test
    @DisplayName("test de getEmailByCity")
    void getEmailByCityTest() {
        //Given initial list of person (that contains emails)
        initListPersons();

        //When we search email by city
        Set<String> response = personServiceImpl.getEmailByCity("Toulon");

        //Then we verify if we have the 3 emails
        assertThat(response).contains("redandblack@email.com", "rougeetnoir@email.com", "noiretrouge@email.com");
    }

    @Test
    @DisplayName("test de getInfosPersonByID without first name")
    void getInfosPersonByIDWithoutFirstNameTest() {
        //Given initial lists of persons, medical records
        List<Person> persons = initListPersons();
        List<MedicalRecord> medicalRecords = initListMedicalRecords();
        initExistingPersonsMedicalRecordsAndFirestation();

        //When we search infos by lastName only
        when(medicalRecordService.getMedicalRecordById(persons.get(0).getId())).thenReturn(medicalRecords.get(0));
        when(medicalRecordService.getMedicalRecordById(persons.get(1).getId())).thenReturn(medicalRecords.get(1));
        when(medicalRecordService.getMedicalRecordById(persons.get(2).getId())).thenReturn(medicalRecords.get(2));
        List<AllInfosPersonDTO> response = personServiceImpl.getInfosPersonByID("gio", null);

        //Then we verify if the response contains 3 persons
        assertThat(response.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("test de getInfosPersonByID with first name")
    void getInfosPersonByIDWithFirstNameTest() {
        //Given initial lists of persons, medical records
        List<Person> persons = initListPersons();
        List<MedicalRecord> medicalRecords = initListMedicalRecords();
        initExistingPersonsMedicalRecordsAndFirestation();

        //When we search infos by lastName and firstName
        when(medicalRecordService.getMedicalRecordById(persons.get(0).getId())).thenReturn(medicalRecords.get(0));
        when(medicalRecordService.getMedicalRecordById(persons.get(1).getId())).thenReturn(medicalRecords.get(1));
        when(medicalRecordService.getMedicalRecordById(persons.get(2).getId())).thenReturn(medicalRecords.get(2));
        List<AllInfosPersonDTO> response = personServiceImpl.getInfosPersonByID("gio", "Gio");

        //Then we verify if the response contains 1 person and the good one
        assertThat(response.size()).isEqualTo(1);
        assertThat(response.get(0).getFirstName()).isEqualTo("Gio");
    }

    @Test
    @DisplayName("test de getInfosPersonByID throwing IllegalArgumentException")
    void getInfosPersonByIDWithOutLastNameTest() {
        //Given

        //When we search without lastName
        try {
            personServiceImpl.getInfosPersonByID(null, "Gio");
        } catch (IllegalArgumentException IArEx) {
            //Then we verify the message passed
            assertThat(IArEx.getMessage()).contains("LastName can't be null, empty or blank");
        }
    }
}