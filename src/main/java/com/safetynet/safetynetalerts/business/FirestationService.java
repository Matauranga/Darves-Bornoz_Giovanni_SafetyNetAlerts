package com.safetynet.safetynetalerts.business;

import com.safetynet.safetynetalerts.exceptions.FirestationNotFoundException;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.repository.FirestationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirestationService {
    @Autowired
    FirestationRepository firestationRepository;

    public List<Firestation> getAllFirestations() {
        return firestationRepository.getAll();
    }

    public void createFirstation(Firestation firestation) {

        firestationRepository.saveOrUpdate(firestation);
    }

    public void updateFirestation(Firestation updateFirestation) {

        Firestation firestation = firestationRepository.getAll()
                .stream()
                .filter(p -> updateFirestation.getId().equals(p.getId()))
                .findFirst()
                .orElseThrow(() -> new FirestationNotFoundException(updateFirestation.getId()))
                .update(updateFirestation);

        firestationRepository.saveOrUpdate(updateFirestation);
    }

    public void deleteFirestation(Firestation firestationToDelete) {
        firestationRepository.delete(firestationToDelete.getId());
    }


}
