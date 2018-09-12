package com.springwebservice.core.abstraction.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface CrudServiceInterface<Entity, Id> {

    JpaRepository<Entity, Id> getRepository();

    Object save(Entity entity);

    Collection saveInBatch(Collection<Entity> collection);

    Object update(Entity entity);

    Object updateInBatch(Collection<Entity> collection);

    void delete(Entity entity);

    void deleteById(Id id);

    void deleteInBatch(Collection<Entity> collection);

    Optional<Entity> findById(Id id);

    Collection findAll();

    void flush();

    Boolean exists(Id id);

}