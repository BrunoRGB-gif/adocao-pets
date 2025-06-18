package com.bruno.adocao_pets.service;

import com.bruno.adocao_pets.model.Mensagem;
import com.bruno.adocao_pets.repository.MensagemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensagemService {

    private final MensagemRepository mensagemRepository;

    public MensagemService(MensagemRepository mensagemRepository) {
        this.mensagemRepository = mensagemRepository;
    }

    public List<Mensagem> listarTodas() {
        return mensagemRepository.findAll();
    }

    public List<Mensagem> mensagensEnviadas(Long idRemetente) {
        return mensagemRepository.findByRemetenteId(idRemetente);
    }

    public List<Mensagem> mensagensRecebidas(Long idDestinatario) {
        return mensagemRepository.findByDestinatarioId(idDestinatario);
    }

    public List<Mensagem> mensagensEntreUsuarios(Long usuario1Id, Long usuario2Id) {
        return mensagemRepository.buscarMensagensEntreUsuarios(usuario1Id, usuario2Id);
    }

    public Mensagem enviarMensagem(Mensagem mensagem) {
        mensagem.setDataEnvio(LocalDateTime.now());
        return mensagemRepository.save(mensagem);
    }

    public void deletar(Long id) {
        mensagemRepository.deleteById(id);
    }
}
