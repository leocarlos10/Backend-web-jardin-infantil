package com.jardininfantil.web_institucional.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class prueba {

    @GetMapping("/ok")
    public String mensaje() {
        return "Server Working";
    }

    @PreAuthorize("hasRole('ROLE_ACUDIENTE')")
    @GetMapping("/user")
    public ResponseEntity<?> user() {
        return ResponseEntity.ok("OK");
    }

    // ROLE_ADMIN solo roles admin pueden acceder a este endpoint.
    // Cualquier otro usuario sera rechazado.

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> admin() {
        return ResponseEntity.ok("Admin info");
    }
}
