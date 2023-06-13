package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import org.junit.jupiter.api.Disabled;
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
    void getByIdTest() {
        // GIVEN
        final String id = "Paul-Machin";
        final MedicalRecord expectedMedicalRecord = new MedicalRecord("Paul", "Machin", null, null, null);
        // WHEN
        when(dataStorage.getMedicalRecords()).thenReturn(List.of(expectedMedicalRecord));
        final Optional<MedicalRecord> response = medicalRecordRepositoryImpl.getById(id);
        //THEN
        assertThat(response).isNotEmpty()
                .contains(expectedMedicalRecord);
    }

    @Test
    void getByIdNotFoundThrowException() {
        // GIVEN an id not exists
        final String id = "Paul-Machin";
        // WHEN
        when(dataStorage.getMedicalRecords()).thenReturn(List.of());
        final Optional<MedicalRecord> response = medicalRecordRepositoryImpl.getById(id);
        //THEN
        assertThat(response).isEmpty();
    }

    @Test
    void getAllTest() {
        // GIVEN
        final MedicalRecord expectedMedicalRecord = new MedicalRecord("Paul", "Machin", null,
                list(null), //TODO
                list(null));

        //WHEN
        when(dataStorage.getMedicalRecords()).thenReturn(List.of(expectedMedicalRecord));
        List<MedicalRecord> listTest = medicalRecordRepositoryImpl.getAll();
        //THEN
        verify(dataStorage, times(1)).getMedicalRecords();
        assertThat(listTest).isNotEmpty();

    }

    @Test
    void createMedicalRecordTest() {
        // GIVEN
        final MedicalRecord expectedMedicalRecord = new MedicalRecord("Paul", "Machin", null,
                list(null),
                list(null));
        //WHEN
        medicalRecordRepositoryImpl.saveOrUpdate(expectedMedicalRecord);
        when(dataStorage.getFirestations()).thenReturn(new ArrayList<>(List.of()));

        //THEN
        verify(dataStorage, times(2)).getMedicalRecords();

    }

    @Test
    void updateMedicalRecordTest() {
        // GIVEN
        final MedicalRecord expectedMedicalRecord = new MedicalRecord("Paul", "Machin", null,
                list(null),
                list(null));
        final MedicalRecord modifyMedicalRecord = new MedicalRecord("Paul", "Machin", "10/10/1100",
                list("propane:NoLimit"),
                list("ethanol"));

        when(dataStorage.getMedicalRecords()).thenReturn(new ArrayList<>(List.of(expectedMedicalRecord)));
        medicalRecordRepositoryImpl.saveOrUpdate(modifyMedicalRecord);

        //THEN
        verify(dataStorage, times(3)).getMedicalRecords();
    }


    @Disabled
    @Test
    void updateMedicalRecordNotFoundExceptionTest() {//TODO : inutile car si non trouver alors cest getId qui renvoie l'err ??
        // GIVEN
        final MedicalRecord expectedMedicalRecord = new MedicalRecord("Paul", "Machin", "10/10/1100",
                list("propane:NoLimit"),
                list("ethanol"));
        final MedicalRecord modifyMedicalRecord = new MedicalRecord("Paul", "Ricard", "",
                list(),
                list());
      /*  when(dataStorage.getMedicalRecords()).thenReturn(List.of(expectedMedicalRecord));
        medicalRecordRepositoryImpl.update(modifyMedicalRecord);

        //THEN
        verify(dataStorage, times(1)).getMedicalRecords();
        assertThat(expectedMedicalRecord.getBirthdate()).isNotEqualTo(modifyMedicalRecord.getBirthdate());
        assertThat(expectedMedicalRecord.getMedications()).isNotEqualTo(modifyMedicalRecord.getMedications());
        assertThat(expectedMedicalRecord.getAllergies()).isNotEqualTo(modifyMedicalRecord.getAllergies());*/
    }

    @Test
    void deleteMedicalRecordTest() {
        final MedicalRecord expectedMedicalRecord = new MedicalRecord("Paul", "Machin", null,
                list(null),
                list(null));
        //WHEN
        when(dataStorage.getMedicalRecords()).thenReturn(new ArrayList<>(List.of(expectedMedicalRecord)));
        medicalRecordRepositoryImpl.delete(expectedMedicalRecord.getId());
        //THEN
        verify(dataStorage, times(2)).getMedicalRecords();

    }

    @Test
    void deleteMedicalRecordNotFoundExceptionTest() {
        final MedicalRecord expectedMedicalRecord = new MedicalRecord("Paul", "Machin", null,
                list(null),
                list(null));
        //WHEN
        when(dataStorage.getMedicalRecords()).thenReturn(new ArrayList<>(List.of()));
        medicalRecordRepositoryImpl.delete(expectedMedicalRecord.getId());
        //THEN
        verify(dataStorage, times(1)).getMedicalRecords();
    }
}

