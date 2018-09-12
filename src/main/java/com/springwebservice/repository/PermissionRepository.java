package com.springwebservice.repository;

import com.springwebservice.entity.PermissionEntity;
import com.springwebservice.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long>  {

    @Query("select new PermissionEntity(p.id, p.permission, p.active) from PermissionEntity p inner join p.rolePermission rp where rp.role in (:roles) and p.active = true")
    Set<PermissionEntity> findAllPermissionsByRoles(@Param("roles") Set<RoleEntity> roles);

}