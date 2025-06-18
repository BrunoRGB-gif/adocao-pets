package com.bruno.adocao_pets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String exibirLogin() {
        return "login"; // Carrega templates/login.html
    }
}
