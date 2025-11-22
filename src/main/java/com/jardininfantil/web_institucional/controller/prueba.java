package com.jardininfantil.web_institucional.controller;

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
}
