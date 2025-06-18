package com.bruno.adocao_pets.controller;

import com.bruno.adocao_pets.model.Usuario;
import com.bruno.adocao_pets.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Exibe a tela de cadastro (HTML)
    @GetMapping("/cadastro")
    public String exibirFormularioCadastro() {
        return "cadastro"; // carrega templates/cadastro.html
    }

    // Salva usuário via JSON
    @PostMapping("/api")
    @ResponseBody
    public Usuario salvarViaJson(@RequestBody Usuario usuario) {
        System.out.println(">> RECEBIDO NO JSON API");
        return usuarioService.salvar(usuario);
    }

    // Salva usuário vindo do formulário HTML
    @PostMapping
    public String salvarFormulario(Usuario usuario) {
        usuarioService.salvar(usuario);
        return "redirect:/login";
    }

    // Lista todos os usuários em JSON (API)
    @GetMapping
    @ResponseBody
    public List<Usuario> listar() {
        return usuarioService.listarTodos();
    }

    // Busca usuário por ID (API)
    @GetMapping("/{id}")
    @ResponseBody
    public Usuario buscar(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    // Remove usuário por ID (API)
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
    }
}
