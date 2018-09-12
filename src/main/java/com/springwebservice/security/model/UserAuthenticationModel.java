package com.springwebservice.security.model;

import com.springwebservice.entity.PermissionEntity;
import com.springwebservice.entity.RoleEntity;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticationModel implements Serializable {

    private static final long serialVersionUID = -3649168145542048090L;

    private Long id;
    private String name;
    private String email;
    private Set<RoleEntity> roles;
    private Set<PermissionEntity> permissions;

}