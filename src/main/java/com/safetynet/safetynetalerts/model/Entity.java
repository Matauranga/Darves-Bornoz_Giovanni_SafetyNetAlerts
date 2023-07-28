package com.safetynet.safetynetalerts.model;

public interface Entity<T> {

    String getId();

    T update(T update);
}
