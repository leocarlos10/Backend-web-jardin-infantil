package com.jardininfantil.web_institucional.controller;

import com.jardininfantil.web_institucional.dto.user.LoginRequest;
import com.jardininfantil.web_institucional.dto.user.RegisterRequest;
import com.jardininfantil.web_institucional.service.UserService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(
        @Valid @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(userService.login(request, null));
    }
   
    @PostMapping("/register")
    public ResponseEntity<Object> register(
        @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(userService.register(request));
    }
   
}
