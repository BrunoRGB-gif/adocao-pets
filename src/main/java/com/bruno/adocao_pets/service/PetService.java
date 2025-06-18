package com.bruno.adocao_pets.service;

import com.bruno.adocao_pets.model.Pet;
import com.bruno.adocao_pets.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> listarTodos() {
        return petRepository.findAll();
    }

    public Pet buscarPorId(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    public Pet salvar(Pet pet) {
        return petRepository.save(pet);
    }

    public void deletar(Long id) {
        petRepository.deleteById(id);
    }
}
