package com.safetynet.safetynetalerts.exceptions;

import com.safetynet.safetynetalerts.model.Firestation;

public class FirestationNotFoundException extends NotFoundException {
    public FirestationNotFoundException(String address) {
        super(Firestation.class, address);
    }
}
