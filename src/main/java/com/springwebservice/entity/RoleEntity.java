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
@ToString(of = {"id", "role", "active"})
@EqualsAndHashCode(of = {"id", "role", "active"})
public class RoleEntity implements GrantedAuthority {

    private static final long serialVersionUID = -4075863070641026383L;

    public RoleEntity(Long id, String role, Boolean active) {
        this.id = id;
        this.role = role;
        this.active = active;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(length = 50, unique = true)
    private String role;

    @NotNull
    @Column
    private Boolean active;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private Set<UserRoleEntity> userRoleEntity;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return this.role;
    }

}