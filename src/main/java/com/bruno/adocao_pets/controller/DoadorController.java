package com.bruno.adocao_pets.controller;

import com.bruno.adocao_pets.model.Pet;
import com.bruno.adocao_pets.model.Usuario;
import com.bruno.adocao_pets.repository.PetRepository;
import com.bruno.adocao_pets.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Controller
public class DoadorController {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final String CAMINHO_IMAGENS = "src/main/resources/static/imagens/";

    @GetMapping("/homeDoador")
    public String areaDoador(Model model, Authentication auth) {
        String email = auth.getName();
        Usuario doador = usuarioRepository.findByEmail(email);
        List<Pet> pets = petRepository.findByDono(doador);
        model.addAttribute("pets", pets);
        return "homeDoador";
    }

    @PostMapping("/homeDoador/pets")
    public String cadastrarPet(@RequestParam("nome") String nome,
            @RequestParam("especie") String especie,
            @RequestParam("idade") Integer idade,
            @RequestParam("descricao") String descricao,
            @RequestParam("cidade") String cidade,
            @RequestParam("imagem") MultipartFile imagem,
            Authentication auth) throws IOException {

        String email = auth.getName();
        Usuario dono = usuarioRepository.findByEmail(email);

        String nomeArquivo = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
        Path caminho = Paths.get(CAMINHO_IMAGENS + nomeArquivo);
        Files.createDirectories(caminho.getParent());
        Files.copy(imagem.getInputStream(), caminho, StandardCopyOption.REPLACE_EXISTING);

        Pet pet = new Pet();
        pet.setNome(nome);
        pet.setEspecie(especie);
        pet.setIdade(idade);
        pet.setDescricao(descricao);
        pet.setCidade(cidade);
        pet.setNomeImagem(nomeArquivo);
        pet.setDono(dono);

        petRepository.save(pet);

        return "redirect:/homeDoador";
    }

    // Abrir formulário de edição para pet com id
    @GetMapping("/homeDoador/pets/editar/{id}")
    public String editarPetForm(@PathVariable Long id, Model model, Authentication auth) {
        String email = auth.getName();
        Usuario doador = usuarioRepository.findByEmail(email);
        Pet pet = petRepository.findById(id).orElse(null);
        if (pet == null || !pet.getDono().getId().equals(doador.getId())) {
            return "redirect:/homeDoador"; // sem permissão ou não existe
        }
        model.addAttribute("pet", pet);
        return "editarPet"; // template Thymeleaf com formulário
    }

    // Salvar alterações do pet editado
    @PostMapping("/homeDoador/pets/editar/{id}")
    public String salvarEdicaoPet(@PathVariable Long id,
            @RequestParam("nome") String nome,
            @RequestParam("especie") String especie,
            @RequestParam("idade") Integer idade,
            @RequestParam("descricao") String descricao,
            @RequestParam("cidade") String cidade,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem,
            Authentication auth) throws IOException {

        String email = auth.getName();
        Usuario dono = usuarioRepository.findByEmail(email);

        Pet pet = petRepository.findById(id).orElse(null);
        if (pet == null || !pet.getDono().getId().equals(dono.getId())) {
            return "redirect:/homeDoador"; // sem permissão ou não existe
        }

        pet.setNome(nome);
        pet.setEspecie(especie);
        pet.setIdade(idade);
        pet.setDescricao(descricao);
        pet.setCidade(cidade);

        // Se enviou nova imagem, substitui a antiga
        if (imagem != null && !imagem.isEmpty()) {
            String nomeArquivo = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
            Path caminho = Paths.get(CAMINHO_IMAGENS + nomeArquivo);
            Files.createDirectories(caminho.getParent());
            Files.copy(imagem.getInputStream(), caminho, StandardCopyOption.REPLACE_EXISTING);
            pet.setNomeImagem(nomeArquivo);
        }

        petRepository.save(pet);

        return "redirect:/homeDoador";
    }

    // Excluir pet
    @PostMapping("/homeDoador/pets/excluir/{id}")
    public String excluirPet(@PathVariable Long id, Authentication auth) {
        String email = auth.getName();
        Usuario doador = usuarioRepository.findByEmail(email);
        Pet pet = petRepository.findById(id).orElse(null);
        if (pet != null && pet.getDono().getId().equals(doador.getId())) {
            petRepository.delete(pet);
        }
        return "redirect:/homeDoador";
    }
}
