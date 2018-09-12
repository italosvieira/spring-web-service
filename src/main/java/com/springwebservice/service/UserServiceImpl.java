package com.springwebservice.service;

import com.springwebservice.core.abstraction.CrudServiceAbstraction;
import com.springwebservice.entity.UserEntity;
import com.springwebservice.repository.UserRepository;
import com.springwebservice.repository.custom.interfaces.UserRepositoryCustom;
import com.springwebservice.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl extends CrudServiceAbstraction<UserEntity, Long> implements UserService {

    private final UserRepository repository;

    private final UserRepositoryCustom repositoryCustom;

    @Autowired
    public UserServiceImpl(UserRepository repository, UserRepositoryCustom repositoryCustom) {
        this.repository = repository;
        this.repositoryCustom = repositoryCustom;
    }

    @Override
    public JpaRepository<UserEntity, Long> getRepository() {
        return this.repository;
    }

    @Transactional(readOnly = true)
    public UserEntity findByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List findAllUsersNative() {
        return this.repositoryCustom.findAllUsersNative();
    }

}