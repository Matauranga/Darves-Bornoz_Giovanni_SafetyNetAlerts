package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.MedicalRecord;
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
public class MedicalRecordRepositoryImplTest {
    @Mock
    private DataStorage dataStorage;
    @InjectMocks
    private MedicalRecordRepositoryImpl medicalRecordRepositoryImpl;

    @Test
    @DisplayName("Test de getAll")
    void getAllTest() {
        //Given

        //When we search all medical records
        medicalRecordRepositoryImpl.getAll();

        //Then we verify if this have works correctly
        verify(dataStorage, times(1)).getMedicalRecords();

    }

    @Test
    @DisplayName("Test de getByID")
    void getByIdTest() {
        //Given an id and a medical record corresponding
        final String id = "Paul-Machin";
        final MedicalRecord expectedMedicalRecord = new MedicalRecord("Paul", "Machin", null,
                list((String) null),
                list((String) null));

        //When we search this medical record
        when(dataStorage.getMedicalRecords()).thenReturn(List.of(expectedMedicalRecord));
        final Optional<MedicalRecord> response = medicalRecordRepositoryImpl.getById(id);

        //Then we verify if we have the good medical record
        assertThat(response).isNotEmpty()
                .contains(expectedMedicalRecord);
    }

    @Test
    @DisplayName("Test de getByID if throw an exception")
    void getByIdNotFoundThrowException() {
        //Given an id not existing
        final String id = "Paul-Machin";

        //When we search for this medical record who not existing
        when(dataStorage.getMedicalRecords()).thenReturn(List.of());
        final Optional<MedicalRecord> response = medicalRecordRepositoryImpl.getById(id);

        //Then throw a medicalRecordNotFoundException
        assertThat(response).isEmpty();
    }

    @Test
    @DisplayName("Test de save de la fonction saveOrUpdate")
    void saveMedicalRecordTest() {
        //Given a medical record to save(create)
        final MedicalRecord expectedMedicalRecord = new MedicalRecord("Paul", "Machin", null,
                list((String) null),
                list((String) null));

        //When we save this medical record
        medicalRecordRepositoryImpl.saveOrUpdate(expectedMedicalRecord);
        when(dataStorage.getFirestations()).thenReturn(new ArrayList<>(List.of()));

        //Then we verify if this have works correctly
        verify(dataStorage, times(2)).getMedicalRecords();
    }

    @Test
    @DisplayName("Test de update de la fonction saveOrUpdate")
    void updateMedicalRecordTest() {
        //Given a medical record and new value for this medical record
        final MedicalRecord expectedMedicalRecord = new MedicalRecord("Paul", "Machin", null,
                list((String) null),
                list((String) null));
        final MedicalRecord modifyMedicalRecord = new MedicalRecord("Paul", "Machin", "10/10/1100",
                list("propane:NoLimit"),
                list("ethanol"));

        //When we update the medical record
        when(dataStorage.getMedicalRecords()).thenReturn(new ArrayList<>(List.of(expectedMedicalRecord)));
        medicalRecordRepositoryImpl.saveOrUpdate(modifyMedicalRecord);

        //Then we verify if this have works correctly
        verify(dataStorage, times(3)).getMedicalRecords();
    }

    @Test
    @DisplayName("Test de delete")
    void deleteMedicalRecordTest() {
        //Given a medical record
        final MedicalRecord expectedMedicalRecord = new MedicalRecord("Paul", "Machin", null,
                list((String) null),
                list((String) null));

        //When we delete it
        when(dataStorage.getMedicalRecords()).thenReturn(new ArrayList<>(List.of(expectedMedicalRecord)));
        medicalRecordRepositoryImpl.delete(expectedMedicalRecord.getId());

        //Then we verify if this have works correctly
        verify(dataStorage, times(2)).getMedicalRecords();
    }

    @Test
    @DisplayName("Test de delete if throw an exception")
    void deleteMedicalRecordNotFoundExceptionTest() {
        //Given a medical record not existing
        final MedicalRecord expectedMedicalRecord = new MedicalRecord("Paul", "Machin", null,
                list((String) null),
                list((String) null));

        //When we try to delete it
        when(dataStorage.getMedicalRecords()).thenReturn(new ArrayList<>(List.of()));
        medicalRecordRepositoryImpl.delete(expectedMedicalRecord.getId());

        //Then we verify if it doesn't work
        verify(dataStorage, times(1)).getMedicalRecords();
    }
}

