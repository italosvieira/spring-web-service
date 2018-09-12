package com.springwebservice.service.interfaces;

import com.springwebservice.core.abstraction.interfaces.CrudServiceInterface;
import com.springwebservice.entity.RoleEntity;

import java.util.Set;

public interface RoleService extends CrudServiceInterface<RoleEntity, Long> {
    Set<RoleEntity> findAllRolesByUserId(Long userId);
}