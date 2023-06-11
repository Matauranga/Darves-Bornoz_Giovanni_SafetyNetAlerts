package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.DataStorage;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
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

        //When
        medicalRecordServiceImpl.getAllMedicalRecords();

        //Then
        verify(medicalRecordRepository, times(1)).getAll();
    }

    @Test
    @DisplayName("Test de getMedicalRecordById")
    void getMedicalRecordByIdTest() {
        //Given
        List<MedicalRecord> listMedicalRecords = new ArrayList<>();
        MedicalRecord medicalRecordTest = new MedicalRecord("Mike", "Big", null, null, null);
        listMedicalRecords.add(medicalRecordTest);

        //When
        when(dataStorage.getMedicalRecords()).thenReturn(listMedicalRecords);
        MedicalRecord response = medicalRecordServiceImpl.getMedicalRecordById("Mike-Big");

        //Then
        assertThat(response).isEqualTo(medicalRecordTest);

    }

    @Test
    @DisplayName("Test de createMedicalRecord")
    void createMedicalRecordTest() {
        //Given
        MedicalRecord medicalRecordTest = new MedicalRecord("Mike", "Big", null, null, null);

        //When
        medicalRecordServiceImpl.createMedicalRecord(medicalRecordTest);

        //Then
        verify(medicalRecordRepository, times(1)).saveOrUpdate(any());
    }

    @Test
    @DisplayName("Test de updateMedicalRecord")
    void updateMedicalRecordTest() {
        //Given
        List<MedicalRecord> response = new ArrayList<>();
        MedicalRecord medicalRecordTest = new MedicalRecord("Mike", "Big", null, null, null);
        response.add(medicalRecordTest);
        MedicalRecord newValueOfMedicalRecordTest = new MedicalRecord("Mike", "Big", "10/10/1000", null, null);

        //When
        when(medicalRecordRepository.getAll()).thenReturn(response);
        medicalRecordServiceImpl.updateMedicalRecord(newValueOfMedicalRecordTest);

        //Then
        verify(medicalRecordRepository, times(1)).getAll();
        verify(medicalRecordRepository, times(1)).saveOrUpdate(any());
        assertThat(medicalRecordTest.getBirthdate()).isEqualTo(newValueOfMedicalRecordTest.getBirthdate());
    }

    @Test
    @DisplayName("Test de deleteMedicalRecord")
    void deleteMedicalRecordTest() {
        //Given
        MedicalRecord medicalRecordTest = new MedicalRecord("Mike", "Big", null, null, null);

        //When
        medicalRecordServiceImpl.deleteMedicalRecord(medicalRecordTest);

        //Then
        verify(medicalRecordRepository, times(1)).delete(any());
    }
}