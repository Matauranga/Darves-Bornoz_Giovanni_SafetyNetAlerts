package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.DTO.CountAdultChildByFirestationWithInfosPersonDTO;
import com.safetynet.safetynetalerts.DTO.InfosPersonLivingAtAddressDTO;
import com.safetynet.safetynetalerts.DTO.InfosPersonsLivingAtAddressAndFirestationToCallDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.list;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class FirestationServiceImplTest {
    @InjectMocks
    FirestationServiceImpl firestationServiceImpl;
    @Mock
    FirestationRepository firestationRepository;
    @Mock
    PersonRepository personRepository;
    @Mock
    MedicalRecordRepository medicalRecordRepository;
    @Mock
    MedicalRecordService medicalRecordService;

    private List<Person> initListPersons() {
        List<Person> persons = new ArrayList<>();
        Person person1 = new Person("Gio", "Ggio", "123 Mayol", "Toulon", "101010", "000-111-2222", "rougeetnoir@email.com");
        //child
        Person person2 = new Person("Gio", "Agio", "123 Mayol", "Toulon", "101010", "000-111-3333", "noiretrouge@email.com");
        Person person3 = new Person("Gio", "Bgio", "123 Mayol", "Toulon", "101010", "000-111-4444", "redandblack@email.com");
        Collections.addAll(persons, person1, person2, person3);

        when(personRepository.getAll()).thenReturn(persons);

        return persons;
    }

    private List<MedicalRecord> initListMedicalRecords() {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        MedicalRecord medicalRecord1 = new MedicalRecord("Gio", "Ggio", "01/01/2000", list("propane:NoLimit"), list("ethanol"));
        MedicalRecord medicalRecord2 = new MedicalRecord("Gio", "Agio", "01/01/2020", list("buthane:forSmile"), list("bioethanol"));
        MedicalRecord medicalRecord3 = new MedicalRecord("Gio", "Bgio", "01/01/1999", list("uranium:toDie"), list("methanol"));
        Collections.addAll(medicalRecords, medicalRecord1, medicalRecord2, medicalRecord3);

        when(medicalRecordRepository.getAll()).thenReturn(medicalRecords);

        return medicalRecords;

    }

    private List<Firestation> initListFirestations() {
        List<Firestation> firestations = new ArrayList<>();
        Firestation firestation = new Firestation("123 Mayol", 45);
        firestations.add(firestation);

        when(firestationRepository.getAll()).thenReturn(firestations);

        return firestations;
    }

    private void initExistingPersonsMedicalRecordsAndFirestation() {

        List<Person> persons = initListPersons();
        List<MedicalRecord> medicalRecords = initListMedicalRecords();
        List<Firestation> firestations = initListFirestations();

    }

    @Test
    @DisplayName("test de getAllFirestations")
    void getAllFirestationsTest() {
        //Given
        //When
        firestationServiceImpl.getAllFirestations();

        //Then
        verify(firestationRepository, times(1)).getAll();
    }

    @Test
    @DisplayName("test de createFirstation")
    void createFirstationTest() {
        //Given
        Firestation firestationTest = new Firestation("999 Blv Michou", 99);

        //When
        firestationServiceImpl.createFirestation(firestationTest);

        //Then
        verify(firestationRepository, times(1)).saveOrUpdate(any());

    }

    @Test
    @DisplayName("test de updateFirestationTest")
    void updateFirestationTest() {
        //Given
        List<Firestation> response = new ArrayList<>();
        Firestation firestationTest = new Firestation("999 Blv Michou", 99);
        response.add(firestationTest);
        Firestation newValueOfFirestationTest = new Firestation("999 Blv Michou", 100);

        //When
        when(firestationRepository.getAll()).thenReturn(response);
        firestationServiceImpl.updateFirestation(newValueOfFirestationTest);

        //Then
        verify(firestationRepository, times(1)).getAll();
        verify(firestationRepository, times(1)).saveOrUpdate(any());
        assertThat(firestationTest.getStation()).isEqualTo(newValueOfFirestationTest.getStation());

    }

    @Disabled // TODO : possible de tester dans ce cas l'exception ?
    @Test
    @DisplayName("test de updateFirestationTest")
    void updateAFirestationNotExistingTest() {
        //Given
        List<Firestation> response = new ArrayList<>();
        Firestation newValueOfFirestationTest = new Firestation("999 Blv Michou", 100);

        //When
        when(firestationRepository.getAll()).thenReturn(response);
        firestationServiceImpl.updateFirestation(newValueOfFirestationTest);

        //Then
        verify(firestationRepository, times(1)).getAll();
        verify(firestationRepository, times(0)).saveOrUpdate(any());

    }

    @Test
    @DisplayName("test de deleteFirestationTest")
    void deleteFirestationTest() {
        //Given
        Firestation firestationTest = new Firestation("999 Blv Michou", 99);

        //When
        firestationServiceImpl.deleteFirestation(firestationTest);

        //Then
        verify(firestationRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("test de getPersonsCoverByFirestation")
    void getPersonsCoverByFirestationTest() {
        //Given
        initExistingPersonsMedicalRecordsAndFirestation();
        Person expectedPerson = new Person("Gio", "Agio", "123 Mayol", "Toulon", "101010", "000-111-3333", "noiretrouge@email.com");
        //When
        List<Person> response = firestationServiceImpl.getPersonsCoverByFirestation(45);

        //Then
        verify(firestationRepository, times(1)).getAll();
        verify(personRepository, times(1)).getAll();
        assertThat(response).contains(expectedPerson);
    }

    //Todo : a voir avec Frank quand fct en utilise une autre : getPhone --> getCover
    @Test
    @DisplayName("test de getPhoneByFirestation")
    void getPhoneByFirestationTest() {
        //Given
        initExistingPersonsMedicalRecordsAndFirestation();
        Person expectedPerson = new Person("Gio", "Agio", "123 Mayol", "Toulon", "101010", "000-111-3333", "noiretrouge@email.com");

        //When
        List<String> response = firestationServiceImpl.getPhoneByFirestation(45);

        //Then
        assertThat(response).contains(expectedPerson.getPhone());

    }

    //TODO : test a revoir absolument
    @Test
    @DisplayName("test de personsListCoveredByFirestationAndAdultChildCount")
    void personsListCoveredByFirestationAndAdultChildCountTest() {

        List<Person> persons = initListPersons();
        List<MedicalRecord> medicalRecords = initListMedicalRecords();

        //Given
        initExistingPersonsMedicalRecordsAndFirestation();
        Integer expectedAdultCount = 2;
        Integer expectedChildCount = 1;
        //TODO : Comment tester ?
        //Person expectedChild = new Person("Gio", "Agio", "123 Mayol", "Toulon", "101010", "000-111-3333", "noiretrouge@email.com");

        //When
        when(medicalRecordService.getMedicalRecordById(persons.get(0).getId())).thenReturn(medicalRecords.get(0));
        when(medicalRecordService.getMedicalRecordById(persons.get(1).getId())).thenReturn(medicalRecords.get(1));
        when(medicalRecordService.getMedicalRecordById(persons.get(2).getId())).thenReturn(medicalRecords.get(2));
        CountAdultChildByFirestationWithInfosPersonDTO response = firestationServiceImpl.personsListCoveredByFirestationAndAdultChildCount(45);

        //Then
        assertThat(response.getInfosPersonByFirestationDTO()).isNotEmpty(); //ici
        assertThat(response.getChildNumber()).isEqualTo(expectedChildCount);
        assertThat(response.getAdultNumber()).isEqualTo(expectedAdultCount);


    }

    @Test
    @DisplayName("test de getInfosPersonsLivingAtAddressAndFirestationToCall")
    void getInfosPersonsLivingAtAddressAndFirestationToCallTest() {
        //Given
        List<Person> persons = initListPersons();
        List<MedicalRecord> medicalRecords = initListMedicalRecords();
        initExistingPersonsMedicalRecordsAndFirestation();
        Integer expectedFirestation = 45;

        //When
        when(medicalRecordService.getMedicalRecordById(persons.get(0).getId())).thenReturn(medicalRecords.get(0));
        when(medicalRecordService.getMedicalRecordById(persons.get(1).getId())).thenReturn(medicalRecords.get(1));
        when(medicalRecordService.getMedicalRecordById(persons.get(2).getId())).thenReturn(medicalRecords.get(2));
        InfosPersonsLivingAtAddressAndFirestationToCallDTO response = firestationServiceImpl.getInfosPersonsLivingAtAddressAndFirestationToCall("123 Mayol");

        //Then
        assertThat(response.getPersonsLivingAtAddress()).isNotEmpty();
        assertThat(response.getFirestationToCall()).isEqualTo(expectedFirestation);
    }

    @Test
    @DisplayName("test de getHouseholdServedByFirestation")
    void getHouseholdServedByFirestationTest() { //TODO : faut il tester avec plusieurs station ou 1 suffit ?
        //Given
        List<Person> persons = initListPersons();
        List<MedicalRecord> medicalRecords = initListMedicalRecords();
        initExistingPersonsMedicalRecordsAndFirestation();

        //When
        when(medicalRecordService.getMedicalRecordById(persons.get(0).getId())).thenReturn(medicalRecords.get(0));
        when(medicalRecordService.getMedicalRecordById(persons.get(1).getId())).thenReturn(medicalRecords.get(1));
        when(medicalRecordService.getMedicalRecordById(persons.get(2).getId())).thenReturn(medicalRecords.get(2));
        List<InfosPersonLivingAtAddressDTO> response = firestationServiceImpl.getHouseholdServedByFirestation(Collections.singleton(45));
        //Then
        assertThat(response).isNotEmpty();

    }
}