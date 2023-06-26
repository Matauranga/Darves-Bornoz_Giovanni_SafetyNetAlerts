package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Firestation;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Test de getAll")
    void getAllTest() {
        //Given

        //When we search all firestations
        firestationRepositoryImpl.getAll();

        //Then we verify if this have works correctly
        verify(dataStorage, times(1)).getFirestations();
    }

    @Test
    @DisplayName("Test de getById")
    void getById() {
        //Given an id and a firestation corresponding
        final String id = "123 Mayol";
        final Firestation expectedFirestation = new Firestation("123 Mayol", 20);

        //When we search this firestation
        when(dataStorage.getFirestations()).thenReturn(List.of(expectedFirestation));
        final Optional<Firestation> response = firestationRepositoryImpl.getById(id);

        //Then we verify if we have the good firestation
        assertThat(response).isNotEmpty()
                .contains(expectedFirestation);
    }

    @Test
    @DisplayName("Test de getById if throw an exception")
    void getByIdNotFoundTest() {
        //Given an id not existing
        final String id = "20";

        //When we search for this firestation who not existing
        when(dataStorage.getFirestations()).thenReturn(List.of());
        final Optional<Firestation> response = firestationRepositoryImpl.getById(id);

        //Then throw a firestationNotFoundException
        assertThat(response).isEmpty();
    }


    @Test
    @DisplayName("Test de save de la fonction saveOrUpdate")
    void saveFirestationTest() {
        //Given a firestation to save(create)
        final Firestation expectedFirestation = new Firestation("123 Soleil", 30);

        //When we save this firestation
        when(dataStorage.getFirestations()).thenReturn(new ArrayList<>(List.of()));
        firestationRepositoryImpl.saveOrUpdate(expectedFirestation);

        //Then we verify if this have works correctly
        verify(dataStorage, times(2)).getFirestations();
    }

    @Test
    @DisplayName("Test de update de la fonction saveOrUpdate")
    void updateFirestationTest() {
        //Given a firestation and new value for this firestation
        final Firestation expectedFirestation = new Firestation("123 Soleil", 30);
        final Firestation wantedFirestation = new Firestation("123 Soleil", 25);

        //When we update the firestation
        when(dataStorage.getFirestations()).thenReturn(new ArrayList<>(List.of(expectedFirestation)));
        firestationRepositoryImpl.saveOrUpdate(wantedFirestation);

        //Then we verify if this have works correctly
        verify(dataStorage, times(3)).getFirestations();
    }


    @Test
    @DisplayName("Test de delete")
    void deleteFirestationTest() {
        //Given a firestation
        final Firestation expectedFirestation = new Firestation("123 Soleil", 30);

        //When we delete it
        when(dataStorage.getFirestations()).thenReturn(new ArrayList<>(List.of(expectedFirestation)));
        firestationRepositoryImpl.delete(expectedFirestation.getId());

        //Then we verify if this have works correctly
        verify(dataStorage, times(2)).getFirestations();
    }

    @Test
    @DisplayName("Test de delete if throw an exception")
    void deleteFirestationNotFoundExceptionTest() {
        //Given a firestation not existing
        final Firestation expectedFirestation = new Firestation("123 Soleil", 30);

        //When we try to delete it
        when(dataStorage.getFirestations()).thenReturn(new ArrayList<>(List.of()));
        firestationRepositoryImpl.delete(expectedFirestation.getId());

        //Then we verify if it doesn't work
        verify(dataStorage, times(1)).getFirestations();
    }
}

