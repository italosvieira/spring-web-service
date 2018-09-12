package com.springwebservice.service;

import com.springwebservice.core.abstraction.CrudServiceAbstraction;
import com.springwebservice.entity.RolePermissionEntity;
import com.springwebservice.repository.RolePermissionRepository;
import com.springwebservice.service.interfaces.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionServiceImpl extends CrudServiceAbstraction<RolePermissionEntity, Long> implements RolePermissionService {

    private final RolePermissionRepository repository;

    @Autowired
    public RolePermissionServiceImpl(RolePermissionRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<RolePermissionEntity, Long> getRepository() {
        return this.repository;
    }

}