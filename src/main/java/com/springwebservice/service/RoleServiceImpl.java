package com.springwebservice.service;

import com.springwebservice.core.abstraction.CrudServiceAbstraction;
import com.springwebservice.entity.RoleEntity;
import com.springwebservice.repository.RoleRepository;
import com.springwebservice.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class RoleServiceImpl extends CrudServiceAbstraction<RoleEntity, Long> implements RoleService {

    private final RoleRepository repository;

    @Autowired
    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<RoleEntity, Long> getRepository() {
        return this.repository;
    }

    @Transactional(readOnly = true)
    public Set<RoleEntity> findAllRolesByUserId(Long userId) {
        return this.repository.findAllRolesByUserId(userId);
    }

}