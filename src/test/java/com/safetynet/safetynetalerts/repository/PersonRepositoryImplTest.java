package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Person;
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
class PersonRepositoryImplTest {
    @Mock
    private DataStorage dataStorage;
    @InjectMocks
    private PersonRepositoryImpl personRepositoryImpl;

    @Test
    @DisplayName("Test de getAll")
    void getAllTest() {
        //Given

        //When we search all persons
        personRepositoryImpl.getAll();

        //Then we verify if this have works correctly
        verify(dataStorage, times(1)).getPersons();
    }

    @Test
    @DisplayName("Test de getById")
    void getByIdTest() {
        //Given an id and a person corresponding
        final String id = "Paul-Machin";
        final Person expectedPerson = new Person("Paul", "Machin", null, null, null, null, null);

        //When we search this person
        when(dataStorage.getPersons()).thenReturn(List.of(expectedPerson));
        final Optional<Person> response = personRepositoryImpl.getById(id);

        //Then we verify if we have the good person
        assertThat(response).isNotEmpty()
                .contains(expectedPerson);
    }

    @Test
    @DisplayName("Test de getById if throw an exception")
    void getByIdNotFoundThrowException() {
        //Given an id not exists
        final String id = "Paul-Machin";

        //When we search this person
        when(dataStorage.getPersons()).thenReturn(List.of());
        final Optional<Person> response = personRepositoryImpl.getById(id);

        //THEN throw a PersonNotFoundException
        assertThat(response).isEmpty();
    }

   /* @Test
    void getByByFirstnameAndLastname() {
        // GIVEN an id not exists
        final Person expectedPerson = new Person("Paul", "Machin", null, null, null, null, null);
        // WHEN
        when(dataStorage.getPersons()).thenReturn(List.of(expectedPerson));
        final Optional<Person> response = personRepositoryImpl.getByFirstnameAndLastname(expectedPerson.getFirstName(), expectedPerson.getLastName());
        //THEN the response is the expected person
        assertThat(response).isNotEmpty()
                .contains(expectedPerson);
    }

    @Test
    void getByFirstnameAndLastnameNotFoundThrowException() {
        // GIVEN an id not exists
        final String firstname = "Paul";
        final String lastname = "Machin";
        // WHEN
        when(dataStorage.getPersons()).thenReturn(List.of());
        final Optional<Person> response = personRepositoryImpl.getByFirstnameAndLastname(firstname, lastname);
        //THEN throw a PersonNotFoundException
        assertThat(response).isEmpty();
    }*/


    @Test
    @DisplayName("Test de save de la fonction saveOrUpdate")
    void savePersonTest() {
        //Given a person to save(create)
        final Person personToCreate = new Person("Gio", "Agio", "123 Mayol", "Toulon", "101010", "000-111-2222", "rougeetnoir@email.com");

        //When we save this person
        personRepositoryImpl.saveOrUpdate(personToCreate);
        when(dataStorage.getPersons()).thenReturn(new ArrayList<>(List.of()));

        //Then we verify if this have works correctly
        verify(dataStorage, times(2)).getPersons();
    }

    @Test
    @DisplayName("Test de update de la fonction saveOrUpdate")
    void updatePersonTest() {
        //Given a person and new value for this person
        final Person expectedPerson = new Person("Paul", "Machin", null, null, null, null, null);
        final Person modifyPerson = new Person("Paul", "Machin", "123 Mayol", null, null, null, null);

        //When we update the person
        when(dataStorage.getPersons()).thenReturn(new ArrayList<>(List.of(expectedPerson)));
        personRepositoryImpl.saveOrUpdate(modifyPerson);

        //Then we verify if this have works correctly
        verify(dataStorage, times(3)).getPersons();
    }

    @Test
    @DisplayName("Test de delete")
    void deletePersonTest() {
        //Given a person
        final Person personToDelete = new Person("Paul", "Machin", null, null, null, null, null);

        //When we delete it
        when(dataStorage.getPersons()).thenReturn(new ArrayList<>(List.of(personToDelete)));
        personRepositoryImpl.delete(personToDelete.getId());

        //Then we verify if this have works correctly
        verify(dataStorage, times(2)).getPersons();
    }

    @Test
    @DisplayName("Test de getAll")
    void deletePersonNotFoundTest() {
        //Given a person not existing
        final Person personToDelete = new Person("Paul", "Machin", null, null, null, null, null);

        //When we try to delete it
        when(dataStorage.getPersons()).thenReturn(new ArrayList<>(List.of()));
        personRepositoryImpl.delete(personToDelete.getId());

        //Then we verify if it doesn't work
        verify(dataStorage, times(1)).getPersons();
    }
}