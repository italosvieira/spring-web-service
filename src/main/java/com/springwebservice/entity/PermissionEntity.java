package com.springwebservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(of = {"id", "permission", "active"})
@EqualsAndHashCode(of = {"id", "permission", "active"})
public class PermissionEntity implements GrantedAuthority {

    private static final long serialVersionUID = -2424777450391052527L;

    public PermissionEntity(Long id, String permission, Boolean active) {
        this.id = id;
        this.permission = permission;
        this.active = active;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(length = 50, unique = true)
    private String permission;

    @NotNull
    @Column
    private Boolean active;

    @JsonIgnore
    public String getAuthority() {
        return this.permission;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "permission")
    Set<RolePermissionEntity> rolePermission;

}