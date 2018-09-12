package com.springwebservice;

import com.springwebservice.core.utils.SecurityUtils;
import com.springwebservice.entity.*;
import com.springwebservice.service.interfaces.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApplicationDataBaseLoader implements CommandLineRunner {

    private static final String USER = "user";
    private static final String EMAIL = "@email.com";

    private final UserService userService;
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final UserRoleService userRoleService;
    private final RolePermissionService rolePermissionService;

    @Autowired
    public ApplicationDataBaseLoader(UserService userService, RoleService roleService, PermissionService permissionService,
                                     UserRoleService userRoleService, RolePermissionService rolePermissionService) {
        this.userService = userService;
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.userRoleService = userRoleService;
        this.rolePermissionService = rolePermissionService;
    }

    @Override
    public void run(String... strings) {
        // Save all permissions.
        PermissionEntity create = new PermissionEntity(null, "create_user", Boolean.TRUE);
        this.permissionService.save(create);

        PermissionEntity read = new PermissionEntity(null, "read_user", Boolean.TRUE);
        this.permissionService.save(read);

        PermissionEntity update = new PermissionEntity(null, "update_user", Boolean.TRUE);
        this.permissionService.save(update);

        PermissionEntity delete = new PermissionEntity(null, "delete_user", Boolean.TRUE);
        this.permissionService.save(delete);

        // Save all roles.
        RoleEntity administratorRole = new RoleEntity(null, "Administrator", Boolean.TRUE);
        this.roleService.save(administratorRole);

        RoleEntity regularUserRole = new RoleEntity(null, "Regular User", Boolean.TRUE);
        this.roleService.save(regularUserRole);

        // Save administartor.
        UserEntity administrator = new UserEntity();
        administrator.setName("administrator");
        administrator.setEmail("administrator@email.com");
        administrator.setPassword(SecurityUtils.B_CRYPT_PASSWORD_ENCODER.encode("administrator"));
        administrator.setActive(Boolean.TRUE);
        this.userService.save(administrator);

        // Save regular users.
        List<UserEntity> users = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            UserEntity u = new UserEntity();
            u.setName(USER.concat(StringUtils.SPACE).concat(String.valueOf(i)));
            u.setEmail(USER.concat(String.valueOf(i)).concat(EMAIL));
            u.setPassword(SecurityUtils.B_CRYPT_PASSWORD_ENCODER.encode(USER.concat(String.valueOf(i))));
            u.setActive(Boolean.TRUE);
            users.add(u);
        }

        this.userService.saveInBatch(users);

        // Associate administrator with administratorRole.
        this.userRoleService.save(new UserRoleEntity(null, administratorRole, administrator));

        // Associate all regular users with regularUsers role.
        users.forEach(u -> this.userRoleService.save(new UserRoleEntity(null, regularUserRole, u)));

        // Roles permission.
        this.rolePermissionService.save(new RolePermissionEntity(null, administratorRole, create));
        this.rolePermissionService.save(new RolePermissionEntity(null, administratorRole, read));
        this.rolePermissionService.save(new RolePermissionEntity(null, administratorRole, update));
        this.rolePermissionService.save(new RolePermissionEntity(null, administratorRole, delete));

        //Regular user
        this.rolePermissionService.save(new RolePermissionEntity(null, regularUserRole, read));
    }

}