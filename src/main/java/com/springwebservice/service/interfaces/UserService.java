package com.springwebservice.service.interfaces;

import com.springwebservice.core.abstraction.interfaces.CrudServiceInterface;
import com.springwebservice.entity.UserEntity;

import java.util.List;

public interface UserService extends CrudServiceInterface<UserEntity, Long> {
    UserEntity findByEmail(String email);
    List findAllUsersNative();
}