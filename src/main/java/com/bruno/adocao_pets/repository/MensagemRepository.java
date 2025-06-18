package com.bruno.adocao_pets.repository;

import com.bruno.adocao_pets.model.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

    List<Mensagem> findByRemetenteId(Long remetenteId);

    List<Mensagem> findByDestinatarioId(Long destinatarioId);

    @Query("SELECT m FROM Mensagem m WHERE " +
            "(m.remetente.id = :usuario1 AND m.destinatario.id = :usuario2) " +
            "OR (m.remetente.id = :usuario2 AND m.destinatario.id = :usuario1) " +
            "ORDER BY m.dataEnvio ASC")
    List<Mensagem> buscarMensagensEntreUsuarios(@Param("usuario1") Long usuario1, @Param("usuario2") Long usuario2);
}
