package com.springwebservice.service.interfaces;

import com.springwebservice.core.abstraction.interfaces.CrudServiceInterface;
import com.springwebservice.entity.PermissionEntity;
import com.springwebservice.entity.RoleEntity;

import java.util.Set;

public interface PermissionService extends CrudServiceInterface<PermissionEntity, Long>  {
    Set<PermissionEntity> findAllPermissionsByRoles(Set<RoleEntity> roles);
}