package com.bruno.adocao_pets.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String especie;
    private Integer idade;
    private String descricao;
    private String cidade;

    private String nomeImagem; // campo novo para salvar o nome da imagem

    @ManyToOne
    @JoinColumn(name = "dono_id")
    private Usuario dono;
}
