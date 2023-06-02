package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Entity;

import java.util.List;
import java.util.Optional;


public interface CrudRepository<T extends Entity> {

    Optional<T> getById(String id);

    List<T> getAll();

    T saveOrUpdate(T entity);

    void delete(String id);
}
