package com.springwebservice.security.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LoginModel implements Serializable {

    private static final long serialVersionUID = 5112574316610915807L;

    private String login;
    private String password;

}