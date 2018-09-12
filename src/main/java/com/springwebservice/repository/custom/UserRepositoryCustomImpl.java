package com.springwebservice.repository.custom;

import com.springwebservice.core.abstraction.CrudCustomRepositoryAbstraction;
import com.springwebservice.entity.UserEntity;
import com.springwebservice.repository.custom.interfaces.UserRepositoryCustom;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryCustomImpl extends CrudCustomRepositoryAbstraction implements UserRepositoryCustom {

    public List findAllUsersNative() {
        return this.getHibernateSession()
                .createNativeQuery("select u.id, u.name, u.email, u.active, u.password from user_entity as u where u.name != :name and u.active = true", UserEntity.class)
                .setParameter("name", "administrator")
                .getResultList();
    }

}