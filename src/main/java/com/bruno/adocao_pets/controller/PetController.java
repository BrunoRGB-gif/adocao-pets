package com.bruno.adocao_pets.controller;

import com.bruno.adocao_pets.model.Pet;
import com.bruno.adocao_pets.service.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public List<Pet> listar() {
        return petService.listarTodos();
    }

    @GetMapping("/{id}")
    public Pet buscar(@PathVariable Long id) {
        return petService.buscarPorId(id);
    }

    // Se precisar, pode habilitar POST da API REST, mas não conflite com
    // DoadorController
    // @PostMapping
    // public Pet salvar(@RequestBody Pet pet) {
    // return petService.salvar(pet);
    // }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        petService.deletar(id);
    }
}
