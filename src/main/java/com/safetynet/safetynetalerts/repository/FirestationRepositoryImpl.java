package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.exceptions.NotFoundException;
import com.safetynet.safetynetalerts.model.Firestation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 */
@Log4j2
@Service
public class FirestationRepositoryImpl implements FirestationRepository {
    @Autowired
    DataStorage dataStorage;

    /**
     * @return a list of all firestations.
     */
    @Override
    public List<Firestation> getAll() {
        return dataStorage.getFirestations();
    }

    /**
     * @param id the id of firestation.
     * @return a firestation if she exists.
     */
    @Override
    public Optional<Firestation> getById(String id) {
        Optional<Firestation> firestation = dataStorage.getFirestations().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if (firestation.isEmpty()) {
            log.error("Firestation doesn't exist");
        }
        return firestation;
    }

    /**
     * @param entity a firestation.
     * @return the firestation create or update.
     */
    @Override
    public Firestation saveOrUpdate(Firestation entity) {

        var entityFirestation = getById(entity.getId());

        if (entityFirestation.isPresent()) {
            int index = dataStorage.getFirestations().indexOf(entityFirestation.get());
            dataStorage.getFirestations()
                    .set(index, entity);
        } else {
            dataStorage.getFirestations().add(entity);
        }
        return entity;
    }

    /**
     * @param id the id of firestation to delete.
     */
    @Override
    public void delete(String id) {
        Firestation firestationToDelete = dataStorage.getFirestations()
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst().orElse(null);
        if (firestationToDelete != null) {
            dataStorage.getFirestations().remove(firestationToDelete);
        } else {
            log.error("Firestation not found", new NotFoundException(Firestation.class, id));
        }
    }

}
