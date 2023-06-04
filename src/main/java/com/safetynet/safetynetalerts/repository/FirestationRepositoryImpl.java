package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.exceptions.NotFoundException;
import com.safetynet.safetynetalerts.model.Firestation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Todo: javadoc
@Service
public class FirestationRepositoryImpl implements FirestationRepository {
    private static final Logger logger = LogManager.getLogger("SafetyNet Alerts");
    @Autowired
    DataStorage dataStorage;

    @Override
    public List<Firestation> getAll() {
        return dataStorage.getFirestations();
    }

    @Override
    public Optional<Firestation> getById(String id) {
        Optional<Firestation> firestation = dataStorage.getFirestations().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if (firestation.isEmpty()) {
            logger.info("Firestation doesn't exist");
        }
        return firestation;
    }

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

    //Todo : return infos sur le delete (code derr ou de réussite)?
    @Override
    public void delete(String id) {
        Firestation firestationToDelete = dataStorage.getFirestations()
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst().orElse(null);
        if (firestationToDelete != null) {
            dataStorage.getFirestations().remove(firestationToDelete);
        } else {
            logger.error("Firestation not found", new NotFoundException(Firestation.class, id));
        }
    }

}
