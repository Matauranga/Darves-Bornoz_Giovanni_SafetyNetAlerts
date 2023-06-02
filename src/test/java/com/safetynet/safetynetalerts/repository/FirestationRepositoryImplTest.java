package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Firestation;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FirestationRepositoryImplTest {
    @Mock
    private DataStorage dataStorage;

    @InjectMocks
    private FirestationRepositoryImpl firestationRepositoryImpl;

    @Test
    void getById() {
        // GIVEN
        final String id = "123 Mayol";
        final Firestation expectedFirestation = new Firestation("123 Mayol", 20);
        // WHEN
        when(dataStorage.getFirestations()).thenReturn(List.of(expectedFirestation));
        final Optional<Firestation> response = firestationRepositoryImpl.getById(id);
        // THEN
        assertThat(response).isNotEmpty()
                .contains(expectedFirestation);
    }

    @Test
    void getByIdNotFoundTest() {
        // GIVEN
        final String id = "20";
        // WHEN
        when(dataStorage.getFirestations()).thenReturn(List.of());
        final Optional<Firestation> response = firestationRepositoryImpl.getById(id);
        // THEN
        assertThat(response).isEmpty();
    }

    @Test
    void getAllTest() {
        // GIVEN
        final Firestation expectedFirestation = new Firestation("123 Soleil", 30);
        //WHEN
        when(dataStorage.getFirestations()).thenReturn(List.of(expectedFirestation));
        List<Firestation> listTest = firestationRepositoryImpl.getAll();
        //THEN
        verify(dataStorage, times(1)).getFirestations();
        assertThat(listTest).isNotEmpty();

    }

    @Test
    void saveOrUpdateEntityNotPresentFirestationTest() {
        // GIVEN
        final Firestation expectedFirestation = new Firestation("123 Soleil", 30);
        //WHEN
        when(dataStorage.getFirestations()).thenReturn(new ArrayList<>(List.of()));
        firestationRepositoryImpl.saveOrUpdate(expectedFirestation);
        //THEN TODO: quoi tester ? suffisant ?
        verify(dataStorage, times(2)).getFirestations();

    }

    @Test
    void saveOrUpdateEntityPresentFirestationTest() {
        // GIVEN
        final Firestation expectedFirestation = new Firestation("123 Soleil", 30);
        final Firestation wantedFirestation = new Firestation("123 Soleil", 25);
        //WHEN
        when(dataStorage.getFirestations()).thenReturn(new ArrayList<>(List.of(expectedFirestation)));
        firestationRepositoryImpl.saveOrUpdate(wantedFirestation);
        //THEN TODO: quoi tester ? suffisant ?
        verify(dataStorage, times(3)).getFirestations();

    }


    @Test
    void deleteFirestationTest() {
        // GIVEN
        final Firestation expectedFirestation = new Firestation("123 Soleil", 30);
        //WHEN
        when(dataStorage.getFirestations()).thenReturn(new ArrayList<>(List.of(expectedFirestation)));
        firestationRepositoryImpl.delete(expectedFirestation.getId());
        //THEN TODO: Suffisant?
        verify(dataStorage, times(2)).getFirestations();

    }

    @Test
    void deleteFirestationNotFoundTest() {
        // GIVEN
        final Firestation expectedFirestation = new Firestation("123 Soleil", 30);
        //WHEN
        when(dataStorage.getFirestations()).thenReturn(new ArrayList<>(List.of()));
        firestationRepositoryImpl.delete(expectedFirestation.getId());
        //THEN TODO: Suffisant?
        verify(dataStorage, times(1)).getFirestations();

    }


}

