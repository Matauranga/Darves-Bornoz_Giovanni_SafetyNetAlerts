package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
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
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.list;
import static org.mockito.Mockito.*;

@SpringBootTest
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

        when(medicalRecordRepository.getAll()).thenReturn(medicalRecords);

        return medicalRecords;

    }

    private List<Firestation> initListFirestations() {
        List<Firestation> firestations = List.of(new Firestation("123 Mayol", 45));
        when(firestationRepository.getAll()).thenReturn(firestations);

        return firestations;
    }

    private void initExistingPersonsMedicalRecordsAndFirestation() {

        initListPersons();
        initListMedicalRecords();
        initListFirestations();

    }

    //TODO : test sur optional
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
        //verify(personRepository, times(1)).saveOrUpdate(any());
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
        initListPersons();

        //When
        List<Person> response = personServiceImpl.getPersonsAtAddress("123 Mayol");

        //Then
        verify(personRepository, times(1)).getAll();
        assertThat(response).isNotEmpty();

    }

    @Test
    @DisplayName("test de getChildByAddress")
    void getChildByAddressTest() {
        //Given
        List<Person> persons = initListPersons();
        List<MedicalRecord> medicalRecords = initListMedicalRecords();
        initExistingPersonsMedicalRecordsAndFirestation();

        //When
        when(medicalRecordService.getMedicalRecordById(persons.get(0).getId())).thenReturn(medicalRecords.get(0));
        when(medicalRecordService.getMedicalRecordById(persons.get(1).getId())).thenReturn(medicalRecords.get(1));
        when(medicalRecordService.getMedicalRecordById(persons.get(2).getId())).thenReturn(medicalRecords.get(2));
        List<ChildDTO> response = personServiceImpl.childAlert("123 Mayol");

        //Then
        assertThat(response).isNotEmpty();

    }

    @Test
    @DisplayName("test de getEmailByCity")
    void getEmailByCityTest() {
        //Given
        initListPersons();

        //When
        List<String> response = personServiceImpl.getEmailByCity("Toulon");

        //Then
        assertThat(response).contains("redandblack@email.com", "rougeetnoir@email.com", "noiretrouge@email.com");

    }

    @Test
    @DisplayName("test de getInfosPersonByID without first name")
    void getInfosPersonByIDWithoutFirstNameTest() {
        //Given
        List<Person> persons = initListPersons();
        List<MedicalRecord> medicalRecords = initListMedicalRecords();
        initExistingPersonsMedicalRecordsAndFirestation();

        //When
        when(medicalRecordService.getMedicalRecordById(persons.get(0).getId())).thenReturn(medicalRecords.get(0));
        when(medicalRecordService.getMedicalRecordById(persons.get(1).getId())).thenReturn(medicalRecords.get(1));
        when(medicalRecordService.getMedicalRecordById(persons.get(2).getId())).thenReturn(medicalRecords.get(2));
        //List<InfosPersonForFireAlertDTO> response = personServiceImpl.getInfosPersonByID("gio", null);

        //Then
        // assertThat(response.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("test de getInfosPersonByID with first name")
    void getInfosPersonByIDWithFirstNameTest() {
        //Given
        List<Person> persons = initListPersons();
        List<MedicalRecord> medicalRecords = initListMedicalRecords();
        initExistingPersonsMedicalRecordsAndFirestation();

        //When
        when(medicalRecordService.getMedicalRecordById(persons.get(0).getId())).thenReturn(medicalRecords.get(0));
        when(medicalRecordService.getMedicalRecordById(persons.get(1).getId())).thenReturn(medicalRecords.get(1));
        when(medicalRecordService.getMedicalRecordById(persons.get(2).getId())).thenReturn(medicalRecords.get(2));
        // List<InfosPersonForFireAlertDTO> response = personServiceImpl.getInfosPersonByID("gio", "Gio");

        //Then
        //assertThat(response.size()).isEqualTo(1);
    }
}