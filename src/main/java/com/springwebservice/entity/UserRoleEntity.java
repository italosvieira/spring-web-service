package com.springwebservice.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "role", "user"})
@EqualsAndHashCode(of = {"id", "role", "user"})
public class UserRoleEntity implements Serializable {

    private static final long serialVersionUID = -6269711461929850254L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private RoleEntity role;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

}