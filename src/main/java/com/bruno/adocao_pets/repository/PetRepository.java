package com.bruno.adocao_pets.repository;

import com.bruno.adocao_pets.model.Pet;
import com.bruno.adocao_pets.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByDono(Usuario dono);

    List<Pet> findByDonoNot(Usuario dono);

    List<Pet> findByEspecieIgnoreCaseAndDonoNot(String especie, Usuario dono);
}
