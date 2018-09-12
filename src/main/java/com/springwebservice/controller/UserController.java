package com.springwebservice.controller;

import com.springwebservice.core.abstraction.CrudControllerAbstraction;
import com.springwebservice.core.utils.ApiUtils;
import com.springwebservice.entity.UserEntity;
import com.springwebservice.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(ApiUtils.PRIVATE + "/users")
public class UserController extends CrudControllerAbstraction<UserEntity, Long> {

    private static final long serialVersionUID = -7398781752453764778L;

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAuthority('create_user')")
    public ResponseEntity save(@RequestBody UserEntity userEntity) {
        return ResponseEntity.ok(this.service.save(userEntity));
    }

    @Override
    @PostMapping(path = "/saveInBatch")
    @PreAuthorize("hasAuthority('create_user')")
    public ResponseEntity saveInBatch(@RequestBody Collection<UserEntity> collection) {
        return ResponseEntity.ok(this.service.saveInBatch(collection));
    }

    @Override
    @PutMapping
    @PreAuthorize("hasAuthority('update_user')")
    public ResponseEntity update(@RequestBody UserEntity userEntity) {
        return ResponseEntity.ok(this.service.update(userEntity));
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAuthority('read_user')")
    public ResponseEntity findAll() {
        return ResponseEntity.ok(this.service.findAll());
    }

    @Override
    @DeleteMapping(path = {"/{id}"})
    @PreAuthorize("hasAuthority('delete_user')")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        this.service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping(path = {"/{id}"})
    @PreAuthorize("hasAuthority('read_user')")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        return this.service.findById(id).<ResponseEntity>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok().build());
    }

    @Override
    @GetMapping(path = {"/{id}/exists"})
    @PreAuthorize("hasAuthority('read_user')")
    public ResponseEntity exists(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.service.exists(id));
    }


}