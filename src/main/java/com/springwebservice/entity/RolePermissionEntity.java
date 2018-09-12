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
@ToString(of = {"id", "role", "permission"})
@EqualsAndHashCode(of = {"id", "role", "permission"})
public class RolePermissionEntity implements Serializable {

    private static final long serialVersionUID = 8261429245860668130L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private RoleEntity role;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private PermissionEntity permission;

}