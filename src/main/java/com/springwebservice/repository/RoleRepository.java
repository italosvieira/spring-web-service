package com.springwebservice.repository;

import com.springwebservice.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query("select new RoleEntity(r.id, r.role, r.active) from RoleEntity as r inner join r.userRoleEntity ur where ur.user.id = :userId and r.active = true")
    Set<RoleEntity> findAllRolesByUserId(@Param("userId") Long userId);

}