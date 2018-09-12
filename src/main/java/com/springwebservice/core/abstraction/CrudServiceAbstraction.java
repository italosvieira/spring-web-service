package com.springwebservice.core.abstraction;

import com.springwebservice.core.abstraction.interfaces.CrudServiceInterface;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Component
public abstract class CrudServiceAbstraction<Entity, Id> implements CrudServiceInterface<Entity, Id> {

    @Override
    @Transactional
    public Object save(Entity entity) {
        return this.getRepository().save(entity);
    }

    @Override
    @Transactional
    public Collection saveInBatch(Collection<Entity> collection) {
        return this.getRepository().saveAll(collection);
    }

    @Override
    @Transactional
    public Object update(Entity entity) {
        return this.getRepository().save(entity);
    }

    @Override
    @Transactional
    public Object updateInBatch(Collection<Entity> collection) {
        return this.getRepository().saveAll(collection);
    }

    @Override
    @Transactional
    public void delete(Entity entity) {
        this.getRepository().delete(entity);
    }

    @Override
    @Transactional
    public void deleteById(Id id) {
        this.getRepository().deleteById(id);
    }

    @Override
    @Transactional
    public void deleteInBatch(Collection<Entity> collection) {
        this.getRepository().deleteAll(collection);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Entity> findById(Id id) {
        return this.getRepository().findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection findAll() {
        return this.getRepository().findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean exists(Id id) {
        return this.getRepository().existsById(id);
    }

    @Override
    public void flush() {
        this.getRepository().flush();
    }

}