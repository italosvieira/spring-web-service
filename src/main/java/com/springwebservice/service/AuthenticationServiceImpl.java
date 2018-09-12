package com.springwebservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springwebservice.core.exception.SecurityException;
import com.springwebservice.core.utils.SecurityUtils;
import com.springwebservice.entity.UserEntity;
import com.springwebservice.security.model.LoginModel;
import com.springwebservice.security.model.UserAuthenticationModel;
import com.springwebservice.service.interfaces.AuthenticationService;
import com.springwebservice.service.interfaces.PermissionService;
import com.springwebservice.service.interfaces.RoleService;
import com.springwebservice.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final RoleService roleService;
    private final PermissionService permissionService;

    @Autowired
    public AuthenticationServiceImpl(UserService userService, RoleService roleService, PermissionService permissionService) {
        this.userService = userService;
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

    @Transactional(readOnly = true)
    public UserAuthenticationModel authenticateUser(HttpServletRequest request) throws SecurityException {
        LoginModel credentials;

        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginModel.class);
        } catch (Exception e) {
            throw new SecurityException("Bad credentials!", HttpStatus.UNAUTHORIZED);
        }

        UserEntity user = this.userService.findByEmail(credentials.getLogin());

        if (user == null || !SecurityUtils.B_CRYPT_PASSWORD_ENCODER.matches(credentials.getPassword(), user.getPassword())) {
            throw new SecurityException("Bad credentials!", HttpStatus.UNAUTHORIZED);
        }

        UserAuthenticationModel userAuthenticationModel = new UserAuthenticationModel();
        userAuthenticationModel.setId(user.getId());
        userAuthenticationModel.setName(user.getName());
        userAuthenticationModel.setEmail(user.getEmail());
        userAuthenticationModel.setRoles(this.roleService.findAllRolesByUserId(user.getId()));
        userAuthenticationModel.setPermissions(this.permissionService.findAllPermissionsByRoles(userAuthenticationModel.getRoles()));

        return userAuthenticationModel;
    }

}