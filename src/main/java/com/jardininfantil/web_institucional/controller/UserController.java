package com.jardininfantil.web_institucional.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity; 

@RestController
@RequestMapping("/api/user")
public class UserController{

    @PostMapping()
    public ResponseEntity<?>create() {
        return null ;
    }
}
