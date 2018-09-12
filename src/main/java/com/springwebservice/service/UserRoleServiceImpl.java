package com.springwebservice.service;

import com.springwebservice.core.abstraction.CrudServiceAbstraction;
import com.springwebservice.entity.UserRoleEntity;
import com.springwebservice.repository.UserRoleRepository;
import com.springwebservice.service.interfaces.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends CrudServiceAbstraction<UserRoleEntity, Long>  implements UserRoleService {

    private final UserRoleRepository repository;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<UserRoleEntity, Long> getRepository() {
        return this.repository;
    }

}