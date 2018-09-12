package com.springwebservice.service;

import com.springwebservice.core.abstraction.CrudServiceAbstraction;
import com.springwebservice.entity.PermissionEntity;
import com.springwebservice.entity.RoleEntity;
import com.springwebservice.repository.PermissionRepository;
import com.springwebservice.service.interfaces.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class PermissionServiceImpl extends CrudServiceAbstraction<PermissionEntity, Long> implements PermissionService {

    private final PermissionRepository repository;

    @Autowired
    public PermissionServiceImpl(PermissionRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<PermissionEntity, Long> getRepository() {
        return this.repository;
    }

    @Transactional(readOnly = true)
    public Set<PermissionEntity> findAllPermissionsByRoles(Set<RoleEntity> roles) {
        return this.repository.findAllPermissionsByRoles(roles);
    }

}