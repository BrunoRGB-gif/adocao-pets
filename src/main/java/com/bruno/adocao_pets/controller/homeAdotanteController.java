package com.bruno.adocao_pets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homeAdotanteController {

    @GetMapping("/homeAdotante")
    public String mostrarPaginaAdotante() {
        return "homeAdotante"; // vai procurar o arquivo homeAdotante.html em templates
    }
}
