package com.springwebservice.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(of = {"id", "name", "email", "active"})
@EqualsAndHashCode(of = {"id", "name", "email", "active"})
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1294295938079683349L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(length = 100)
    private String name;

    @NotBlank
    @Column(length = 254, unique = true)
    private String email;

    @NotBlank
    @Column
    private String password;

    @NotNull
    @Column
    private Boolean active;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<UserRoleEntity> userRoleEntity;

}