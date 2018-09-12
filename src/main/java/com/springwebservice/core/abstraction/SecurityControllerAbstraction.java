package com.springwebservice.core.abstraction;

import com.springwebservice.entity.UserEntity;
import com.springwebservice.repository.UserRepository;
import com.springwebservice.security.model.UserAuthenticationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.io.Serializable;

@Controller
public abstract class SecurityControllerAbstraction implements Serializable {

    private static final long serialVersionUID = 6681937407760023821L;

    @Autowired
    private UserRepository userRepository;

    protected UserAuthenticationModel getUserAuthenticationModel() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return (UserAuthenticationModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }

        return null;
    }

    protected UserEntity getUser() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return this.userRepository.findById(this.getUserAuthenticationModel().getId()).orElse(null);
        }

        return null;
    }

}