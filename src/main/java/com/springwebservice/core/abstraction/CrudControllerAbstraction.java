package com.springwebservice.core.abstraction;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
public abstract class CrudControllerAbstraction<Entity, Id> extends SecurityControllerAbstraction {

    private static final long serialVersionUID = -7664995745495948341L;

    public abstract ResponseEntity save(Entity entity);
    public abstract ResponseEntity saveInBatch(Collection<Entity> collection);
    public abstract ResponseEntity update(Entity entity);
    public abstract ResponseEntity findAll();
    public abstract ResponseEntity delete(Id id);
    public abstract ResponseEntity findById(Id id);
    public abstract ResponseEntity exists(Id id);

}