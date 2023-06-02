package com.safetynet.safetynetalerts.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(Class<?> clazz, String id){
        super(clazz.getCanonicalName() +  " not found with id = " + id);
    }

}
