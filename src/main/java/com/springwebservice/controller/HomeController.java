package com.springwebservice.controller;

import com.springwebservice.core.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUtils.PUBLIC + "/home")
public class HomeController {

    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok("Hello World!");
    }

}