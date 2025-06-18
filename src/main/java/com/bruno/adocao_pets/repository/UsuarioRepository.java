package com.bruno.adocao_pets.repository;

import com.bruno.adocao_pets.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email); // necessário para buscar o usuário logado
}
