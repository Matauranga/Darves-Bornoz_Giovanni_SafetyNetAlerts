package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.exceptions.MedicalRecordNotFoundException;
import com.safetynet.safetynetalerts.exceptions.NotFoundException;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.DataStorage;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class MedicalRecordServiceImplTest {
    @InjectMocks
    MedicalRecordServiceImpl medicalRecordServiceImpl;
    @Mock
    MedicalRecordRepository medicalRecordRepository;
    @Mock
    DataStorage dataStorage;

    @Test
    @DisplayName("Test de getAllMedicalRecords")
    void getAllMedicalRecordsTest() {
        //Given

        //When we search all medical records
        medicalRecordServiceImpl.getAllMedicalRecords();

        //Then we verify if this have works correctly
        verify(medicalRecordRepository, times(1)).getAll();
    }

    @Test
    @DisplayName("Test de getMedicalRecordById")
    void getMedicalRecordByIdTest() {
        //Given an initial list of medical records
        List<MedicalRecord> listMedicalRecords = new ArrayList<>();
        MedicalRecord medicalRecordTest = new MedicalRecord("Mike", "Big", null, null, null);
        listMedicalRecords.add(medicalRecordTest);

        //When we search this medical record
        when(dataStorage.getMedicalRecords()).thenReturn(listMedicalRecords);
        MedicalRecord response = medicalRecordServiceImpl.getMedicalRecordById("Mike-Big");

        //Then we verify if we have the good medical record
        assertThat(response).isEqualTo(medicalRecordTest);
    }

    @Test
    @DisplayName("test de getMedicalRecordById throwing NotFoundException")
    void getMedicalRecordByIdThrowingNotFoundExceptionTest() {
        //Given

        //When we send the request
        try {
            medicalRecordServiceImpl.getMedicalRecordById("Jean-Valjean");
        } catch (NotFoundException NotFoundException) {
            //Then we verify the message passed
            assertThat(NotFoundException.getMessage()).contains("Jean-Valjean");
        }
    }

    @Test
    @DisplayName("Test de createMedicalRecord")
    void createMedicalRecordTest() {
        //Given a new medical record
        MedicalRecord medicalRecordTest = new MedicalRecord("Mike", "Big", null, null, null);

        //When we create it
        medicalRecordServiceImpl.createMedicalRecord(medicalRecordTest);

        //Then we verify if this have works correctly
        verify(medicalRecordRepository, times(1)).saveOrUpdate(any());
    }

    @Test
    @DisplayName("Test de updateMedicalRecord")
    void updateMedicalRecordTest() {
        //Given a medical record and new value for this medical record
        MedicalRecord medicalRecordTest = new MedicalRecord("Mike", "Big", null, null, null);
        MedicalRecord newValueOfMedicalRecordTest = new MedicalRecord("Mike", "Big", "10/10/1000", null, null);

        //When we update the medical record
        when(medicalRecordRepository.getAll()).thenReturn(new ArrayList<>(List.of(medicalRecordTest)));
        medicalRecordServiceImpl.updateMedicalRecord(newValueOfMedicalRecordTest);

        //Then we verify the new values of medical record
        verify(medicalRecordRepository, times(1)).getAll();
        assertThat(medicalRecordTest.getBirthdate()).isEqualTo(newValueOfMedicalRecordTest.getBirthdate());
    }

    @Test
    @DisplayName("test de updateMedicalRecord throwing MedicalRecordNotFoundException")
    void updateMedicalRecordThrowingMedicalRecordNotFoundExceptionTest() {
        //Given an initial medical record
        MedicalRecord medicalRecord = new MedicalRecord("Jean", "Valjean", null, null, null);

        //When we send the request
        try {
            medicalRecordServiceImpl.updateMedicalRecord(medicalRecord);
        } catch (MedicalRecordNotFoundException MedRecNotFound) {
            //Then we verify the message passed
            assertThat(MedRecNotFound.getMessage()).contains("Jean-Valjean");
        }
    }

    @Test
    @DisplayName("Test de deleteMedicalRecord")
    void deleteMedicalRecordTest() {
        //Given an initial medical record
        MedicalRecord medicalRecordTest = new MedicalRecord("Mike", "Big", null, null, null);

        //When we delete it
        medicalRecordServiceImpl.deleteMedicalRecord(medicalRecordTest);

        //Then we verify if this have works correctly
        verify(medicalRecordRepository, times(1)).delete(any());
    }
}