package com.bruno.adocao_pets.security;

import com.bruno.adocao_pets.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        UsuarioDetails userDetails = (UsuarioDetails) authentication.getPrincipal();
        Usuario usuario = userDetails.getUsuario();

        if ("adotante".equalsIgnoreCase(usuario.getTipoUsuario())) {
            response.sendRedirect("/homeAdotante");
        } else if ("doador".equalsIgnoreCase(usuario.getTipoUsuario())) {
            response.sendRedirect("/homeDoador");
        } else {
            response.sendRedirect("/login?erro=tipo");
        }
    }
}
