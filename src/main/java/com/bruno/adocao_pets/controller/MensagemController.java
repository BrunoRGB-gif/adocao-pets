package com.bruno.adocao_pets.controller;

import com.bruno.adocao_pets.model.Mensagem;
import com.bruno.adocao_pets.model.Usuario;
import com.bruno.adocao_pets.security.UsuarioDetails;
import com.bruno.adocao_pets.service.MensagemService;
import com.bruno.adocao_pets.service.UsuarioService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class MensagemController {

    private final MensagemService mensagemService;
    private final UsuarioService usuarioService;

    public MensagemController(MensagemService mensagemService, UsuarioService usuarioService) {
        this.mensagemService = mensagemService;
        this.usuarioService = usuarioService;
    }

    // Abrir o chat com as mensagens entre remetente e destinatário
    @GetMapping("/{destinatarioId}")
    public String abrirChat(@PathVariable Long destinatarioId, Model model, Authentication auth) {
        Usuario remetente = ((UsuarioDetails) auth.getPrincipal()).getUsuario();
        Usuario destinatario = usuarioService.buscarPorId(destinatarioId);

        List<Mensagem> conversas = mensagemService.mensagensEntreUsuarios(remetente.getId(), destinatario.getId());

        model.addAttribute("conversas", conversas);
        model.addAttribute("remetente", remetente);
        model.addAttribute("destinatario", destinatario);
        model.addAttribute("mensagem", new Mensagem());

        return "chat";
    }

    // Nova rota fixa para abrir o chat sem destinatário real (para fins de
    // trabalho)
    @GetMapping("/fixo")
    public String abrirChatFixo(Model model, Authentication auth) {
        Usuario remetente = ((UsuarioDetails) auth.getPrincipal()).getUsuario();

        // Destinatário vazio ou fictício
        Usuario destinatarioFalso = new Usuario();
        destinatarioFalso.setId(0L);
        destinatarioFalso.setNome("Usuário Fixo");

        // Sem conversas reais
        List<Mensagem> conversasVazias = List.of();

        model.addAttribute("conversas", conversasVazias);
        model.addAttribute("remetente", remetente);
        model.addAttribute("destinatario", destinatarioFalso);
        model.addAttribute("mensagem", new Mensagem());

        return "chat";
    }

    // Enviar mensagem via formulário
    @PostMapping("/enviar")
    public String enviarMensagem(@ModelAttribute Mensagem mensagem, Authentication auth) {
        Usuario remetente = ((UsuarioDetails) auth.getPrincipal()).getUsuario();
        mensagem.setRemetente(remetente);
        mensagem.setDataEnvio(LocalDateTime.now());

        Usuario destinatario = usuarioService.buscarPorId(mensagem.getDestinatario().getId());
        mensagem.setDestinatario(destinatario);

        mensagemService.enviarMensagem(mensagem);

        return "redirect:/chat/" + destinatario.getId();
    }

    // Listar mensagens enviadas via API
    @GetMapping("/api/enviadas/{idRemetente}")
    @ResponseBody
    public List<Mensagem> listarEnviadas(@PathVariable Long idRemetente) {
        return mensagemService.mensagensEnviadas(idRemetente);
    }

    // Listar mensagens recebidas via API
    @GetMapping("/api/recebidas/{idDestinatario}")
    @ResponseBody
    public List<Mensagem> listarRecebidas(@PathVariable Long idDestinatario) {
        return mensagemService.mensagensRecebidas(idDestinatario);
    }

    // Deletar mensagem via API
    @DeleteMapping("/api/{id}")
    @ResponseBody
    public void deletar(@PathVariable Long id) {
        mensagemService.deletar(id);
    }
}
