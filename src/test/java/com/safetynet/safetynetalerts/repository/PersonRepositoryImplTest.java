package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Person;
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


//TODO revoir les test, ajoutez les displayName etc...
@SpringBootTest
class PersonRepositoryImplTest {
    @Mock
    private DataStorage dataStorage;
    @InjectMocks
    private PersonRepositoryImpl personRepositoryImpl;

    @Test
    void getByIdTest() {
        // GIVEN an id not exists
        final String id = "Paul-Machin";
        final Person expectedPerson = new Person("Paul", "Machin", null, null, null, null, null);
        // WHEN
        when(dataStorage.getPersons()).thenReturn(List.of(expectedPerson));
        final Optional<Person> response = personRepositoryImpl.getById(id);
        //THEN the response is the expected person
        assertThat(response).isNotEmpty()
                .contains(expectedPerson);
    }

    @Test
    void getByIdNotFoundThrowException() {
        // GIVEN an id not exists
        final String id = "Paul-Machin";
        // WHEN
        when(dataStorage.getPersons()).thenReturn(List.of());
        final Optional<Person> response = personRepositoryImpl.getById(id);
        //THEN throw a PersonNotFoundException
        assertThat(response).isEmpty();
    }

    @Test
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
    }

    @Test
    void getAllTest() {
        // GIVEN
        final Person expectedPerson = new Person("Paul", "Machin", null, null, null, null, null);
        //WHEN
        when(dataStorage.getPersons()).thenReturn(List.of(expectedPerson));
        List<Person> listTest = personRepositoryImpl.getAll();
        //THEN
        verify(dataStorage, times(1)).getPersons();
        assertThat(listTest).isNotEmpty();

    }


    @Test
    void createPersonTest() {
        // GIVEN
        final Person personToCreate = new Person("Gio", "Agio", "123 Mayol", "Toulon", "101010", "000-111-2222", "rougeetnoir@email.com");
        //WHEN
        personRepositoryImpl.saveOrUpdate(personToCreate);
        when(dataStorage.getPersons()).thenReturn(new ArrayList<>(List.of()));
        //THEN
        verify(dataStorage, times(2)).getPersons();

    }

    @Test
    void updatePersonTest() {
        // GIVEN
        final Person expectedPerson = new Person("Paul", "Machin", null, null, null, null, null);
        final Person modifyPerson = new Person("Paul", "Machin", "123 Mayol", null, null, null, null);
        // WHEN

        when(dataStorage.getPersons()).thenReturn(new ArrayList<>(List.of(expectedPerson)));
        personRepositoryImpl.saveOrUpdate(modifyPerson);

        //THEN
        verify(dataStorage, times(3)).getPersons();
    }

    @Disabled
    @Test
    void updatePersonNotFoundExceptionTest() { //todo :inutile car si non trouver alors cest getId qui renvoie l'err ??
        // GIVEN
        final Person expectedPerson = new Person("Paul", "Machin", null, null, null, null, null);
        final Person modifyPerson = new Person("Paul", "Ricard", "123 Mayol", null, null, null, null);
        // WHEN

        // hen(dataStorage.getPersons()).thenReturn(List.of(expectedPerson));
        // personRepositoryImpl.update(modifyPerson);

        //THEN
        // verify(dataStorage, times(1)).getPersons();
        // assertThat(expectedPerson).isNotEqualTo(modifyPerson);

    }

    @Test
    void deletePersonTest() {
        // GIVEN
        final Person personToDelete = new Person("Paul", "Machin", null, null, null, null, null);
        // WHEN
        when(dataStorage.getPersons()).thenReturn(new ArrayList<>(List.of(personToDelete)));
        personRepositoryImpl.delete(personToDelete.getId());
        // THEN
        verify(dataStorage, times(2)).getPersons();
    }

    @Test
    void deletePersonNotFoundTest() {
        // GIVEN
        final Person personToDelete = new Person("Paul", "Machin", null, null, null, null, null);
        // WHEN
        when(dataStorage.getPersons()).thenReturn(new ArrayList<>(List.of()));
        personRepositoryImpl.delete(personToDelete.getId());
        // THEN
        verify(dataStorage, times(1)).getPersons();
    }


}