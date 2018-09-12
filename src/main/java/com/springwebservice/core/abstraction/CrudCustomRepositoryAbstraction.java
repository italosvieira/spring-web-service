package com.springwebservice.core.abstraction;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public abstract class CrudCustomRepositoryAbstraction {

    @PersistenceContext
    private EntityManager entityManager;

    protected Session getHibernateSession() {
        return this.entityManager.unwrap(Session.class);
    }

}