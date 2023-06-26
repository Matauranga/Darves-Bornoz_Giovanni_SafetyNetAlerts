package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.DTO.CountAdultChildWithInfosPersonDTO;
import com.safetynet.safetynetalerts.DTO.FireAlertDTO;
import com.safetynet.safetynetalerts.DTO.FloodAlertDTO;
import com.safetynet.safetynetalerts.DTO.InfosPersonCoverByFirestationDTO;
import com.safetynet.safetynetalerts.exceptions.FirestationNotFoundException;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.FirestationRepository;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.list;
import static org.mockito.Mockito.*;

@SpringBootTest
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
        Person person1 = new Person("Gio", "gio", "123 Mayol", "Toulon", "101010", "000-111-2222", "rougeetnoir@email.com");
        //child
        Person person2 = new Person("AGio", "gio", "123 Mayol", "Toulon", "101010", "000-111-3333", "noiretrouge@email.com");
        Person person3 = new Person("BGio", "gio", "123 Mayol", "Toulon", "101010", "000-111-4444", "redandblack@email.com");
        Person person4 = new Person("Gerard", "Calvi", "789 Nous irons aux oeufs", "Toulon", "101010", "000-111-4444", "redandblack@email.com");

        List<Person> persons = List.of(person1, person2, person3, person4);

        when(personRepository.getAll()).thenReturn(persons);

        return persons;
    }

    private List<MedicalRecord> initListMedicalRecords() {
        MedicalRecord medicalRecord1 = new MedicalRecord("Gio", "gio", "01/01/2000", list("propane:NoLimit"), list("ethanol"));
        MedicalRecord medicalRecord2 = new MedicalRecord("AGio", "gio", "01/01/2020", list("butane:forSmile"), list("bioethanol"));
        MedicalRecord medicalRecord3 = new MedicalRecord("BGio", "gio", "01/01/1999", list("uranium:toDie"), list("methanol"));
        MedicalRecord medicalRecord4 = new MedicalRecord("Gerard", "Calvi", "01/01/1900", list("Petrol:1 verre, je vais en mettre 2"), list("Et un peu de poivre en grain"));

        List<MedicalRecord> medicalRecords = List.of(medicalRecord1, medicalRecord2, medicalRecord3, medicalRecord4);

        when(medicalRecordRepository.getAll()).thenReturn(medicalRecords);

        return medicalRecords;
    }

    private List<Firestation> initListFirestations() {
        List<Firestation> firestations = List.of(new Firestation("123 Mayol", 45), new Firestation("789 Nous irons aux oeufs", 1789));
        when(firestationRepository.getAll()).thenReturn(firestations);

        return firestations;
    }

    private void initExistingPersonsMedicalRecordsAndFirestation() {
        initListPersons();
        initListMedicalRecords();
        initListFirestations();
    }

    @Test
    @DisplayName("test de getAllFirestations")
    void getAllFirestationsTest() {
        //Given

        //When we search all firestations
        firestationServiceImpl.getAllFirestations();

        //Then we verify if this have works correctly
        verify(firestationRepository, times(1)).getAll();
    }

    @Test
    @DisplayName("test de createFirestation")
    void createFirestationTest() {
        //Given a new firestation
        Firestation firestationTest = new Firestation("999 Blv Michou", 99);

        //When we create it
        firestationServiceImpl.createFirestation(firestationTest);

        //Then we verify if this have works correctly
        verify(firestationRepository, times(1)).saveOrUpdate(any());
    }

    @Test
    @DisplayName("test de updateFirestation")
    void updateFirestationTest() {
        //Given a firestation and new value for this firestation
        Firestation firestationTest = new Firestation("999 Blv Michou", 99);
        Firestation newValueOfFirestationTest = new Firestation("999 Blv Michou", 100);

        //When we update the firestation
        when(firestationRepository.getAll()).thenReturn(new ArrayList<>(List.of(firestationTest)));
        firestationServiceImpl.updateFirestation(newValueOfFirestationTest);

        //Then we verify the new values of firestation
        verify(firestationRepository, times(1)).getAll();
        assertThat(firestationTest.getStation()).isEqualTo(newValueOfFirestationTest.getStation());
    }

    @Test
    @DisplayName("test de updateFirestation throwing FirestationNotFoundException")
    void updateFirestationThrowingFirestationNotFoundExceptionTest() {
        //Given an initial firestation
        Firestation firestation = new Firestation("123456 Mario", 999000);

        //When we send the request
        try {
            firestationServiceImpl.updateFirestation(firestation);
        } catch (FirestationNotFoundException firestationException) {
            //Then we verify the message passed
            assertThat(firestationException.getMessage()).contains("123456 Mario");
        }
    }

    @Test
    @DisplayName("test de deleteFirestation")
    void deleteFirestationTest() {
        //Given an initial firestation
        Firestation firestationTest = new Firestation("999 Blv Michou", 99);

        //When we delete it
        firestationServiceImpl.deleteFirestation(firestationTest);

        //Then we verify if this have works correctly
        verify(firestationRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("test de getPersonsCoverByFirestation")
    void getPersonsCoverByFirestationTest() {
        //Given initials lists of persons, medical records and firestation and an expected person
        initExistingPersonsMedicalRecordsAndFirestation();
        Person expectedPerson = new Person("AGio", "gio", "123 Mayol", "Toulon", "101010", "000-111-3333", "noiretrouge@email.com");

        //When we search persons cover by the firestation 45
        List<Person> response = firestationServiceImpl.getPersonsCoverByFirestation(45);

        //Then we verify if this have works correctly and if the response contains one of the persons initialized
        verify(firestationRepository, times(1)).getAll();
        verify(personRepository, times(1)).getAll();
        assertThat(response).contains(expectedPerson);
    }

    @Test
    @DisplayName("test de getPhoneByFirestation")
    void getPhoneByFirestationTest() {
        //Given initials lists of persons, medical records and firestation and an expected person
        initExistingPersonsMedicalRecordsAndFirestation();
        Person expectedPerson = new Person("AGio", "gio", "123 Mayol", "Toulon", "101010", "000-111-3333", "noiretrouge@email.com");

        //When we search persons phone number cover by specific firestation
        Set<String> response = firestationServiceImpl.getPhoneByFirestation(45);

        //Then we verify if the response contains one of the persons phone number initialized
        assertThat(response).contains(expectedPerson.getPhone());
    }

    @Test
    @DisplayName("test de personsListCoveredByFirestationAndAdultChildCount")
    void personsListCoveredByFirestationAndAdultChildCountTest() {

        List<Person> persons = initListPersons();
        List<MedicalRecord> medicalRecords = initListMedicalRecords();

        /*Given initials lists of persons, medical records and firestation.
                An expected adult and child count. And an expected person(and his InfosPerson...DTO for testing result)*/
        initExistingPersonsMedicalRecordsAndFirestation();
        Integer expectedAdultCount = 2;
        Integer expectedChildCount = 1;
        Person person = new Person("AGio", "gio", "123 Mayol", "Toulon", "101010", "000-111-3333", "noiretrouge@email.com");
        InfosPersonCoverByFirestationDTO expectedPerson = new InfosPersonCoverByFirestationDTO(person);

        //When we send the request
        when(medicalRecordService.getMedicalRecordById(persons.get(0).getId())).thenReturn(medicalRecords.get(0));
        when(medicalRecordService.getMedicalRecordById(persons.get(1).getId())).thenReturn(medicalRecords.get(1));
        when(medicalRecordService.getMedicalRecordById(persons.get(2).getId())).thenReturn(medicalRecords.get(2));
        CountAdultChildWithInfosPersonDTO response = firestationServiceImpl.personsListCoveredByFirestationAndAdultChildCount(45);

        //Then we verify if the adult/child count is correct and if the
        assertThat(response.getPersonsCoverByFirestation().get(1).getFirstName()).isEqualTo(expectedPerson.getFirstName());
        assertThat(response.getChildNumber()).isEqualTo(expectedChildCount);
        assertThat(response.getAdultNumber()).isEqualTo(expectedAdultCount);
    }

    @Test
    @DisplayName("test de getInfosPersonsLivingAtAddressAndFirestationToCall")
    void getInfosPersonsLivingAtAddressAndFirestationToCallTest() {
        //Given initials lists of persons, medical records and firestation and an expected firestation number
        List<Person> persons = initListPersons();
        List<MedicalRecord> medicalRecords = initListMedicalRecords();
        initExistingPersonsMedicalRecordsAndFirestation();
        Integer expectedFirestation = 45;

        //When we send the request
        when(medicalRecordService.getMedicalRecordById(persons.get(0).getId())).thenReturn(medicalRecords.get(0));
        when(medicalRecordService.getMedicalRecordById(persons.get(1).getId())).thenReturn(medicalRecords.get(1));
        when(medicalRecordService.getMedicalRecordById(persons.get(2).getId())).thenReturn(medicalRecords.get(2));
        FireAlertDTO response = firestationServiceImpl.getInfosPersonsLivingAtAddressAndFirestationToCall("123 Mayol");

        //Then we verify if the firestation to call is the expected
        assertThat(response.getFirestationServingThem()).isEqualTo(expectedFirestation);
    }

    @Test
    @DisplayName("test de getInfosPersonsLivingAtAddressAndFirestationToCall throwing FirestationNotFoundException")
    void getInfosPersonsLivingAtAddressAndFirestationToCallThrowingFirestationNotFoundExceptionTest() {
        //Given

        //When we send the request
        try {
            firestationServiceImpl.getInfosPersonsLivingAtAddressAndFirestationToCall("123 Mayol");
        } catch (FirestationNotFoundException firestationException) {
            //Then we verify the message passed
            assertThat(firestationException.getMessage()).contains("123 Mayol");
        }
    }


    @Test
    @DisplayName("test de getHouseholdServedByFirestation for 1 firestation")
    void getHouseholdsServedByFirestationTest() {
        //Given initials lists of persons, medical records and firestation and an expected address
        List<Person> persons = initListPersons();
        List<MedicalRecord> medicalRecords = initListMedicalRecords();
        initExistingPersonsMedicalRecordsAndFirestation();
        final String expectedAddress = "123 Mayol";

        //When we send the request
        when(medicalRecordService.getMedicalRecordById(persons.get(0).getId())).thenReturn(medicalRecords.get(0));
        when(medicalRecordService.getMedicalRecordById(persons.get(1).getId())).thenReturn(medicalRecords.get(1));
        when(medicalRecordService.getMedicalRecordById(persons.get(2).getId())).thenReturn(medicalRecords.get(2));
        List<FloodAlertDTO> response = firestationServiceImpl.getHouseholdServedByFirestation(Set.of(45));

        //Then we verify if the response contain
        assertThat(response.get(0).getAddress()).isEqualTo(expectedAddress);
    }

    @Test
    @DisplayName("test de getHouseholdServedByFirestation for several firestation")
    void getHouseholdsServedByFirestationForSeveralFirestationTest() {
        //Given initials lists of persons, medical records and firestation and an expected address
        List<Person> persons = initListPersons();
        List<MedicalRecord> medicalRecords = initListMedicalRecords();
        initExistingPersonsMedicalRecordsAndFirestation();
        final String expectedAddress1 = "123 Mayol";
        final String expectedAddress2 = "789 Nous irons aux oeufs";

        //When we send the request
        when(medicalRecordService.getMedicalRecordById(persons.get(0).getId())).thenReturn(medicalRecords.get(0));
        when(medicalRecordService.getMedicalRecordById(persons.get(1).getId())).thenReturn(medicalRecords.get(1));
        when(medicalRecordService.getMedicalRecordById(persons.get(2).getId())).thenReturn(medicalRecords.get(2));
        when(medicalRecordService.getMedicalRecordById(persons.get(3).getId())).thenReturn(medicalRecords.get(3));
        List<FloodAlertDTO> response = firestationServiceImpl.getHouseholdServedByFirestation(Set.of(45, 1789));

        //Then we verify if the response contain
        assertThat(response.get(0).getAddress()).isEqualTo(expectedAddress1);
        assertThat(response.get(1).getAddress()).isEqualTo(expectedAddress2);
    }
}