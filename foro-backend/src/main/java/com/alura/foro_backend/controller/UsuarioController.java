package com.alura.foro_backend.controller;

import com.alura.foro_backend.entity.Usuario;
import com.alura.foro_backend.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/users")
@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/me")
    public ResponseEntity<Usuario> usuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Usuario usuarioActual = (Usuario) authentication.getPrincipal();

        return ResponseEntity.ok(usuarioActual);
    }

    @GetMapping("/")
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = usuarioService.allUsers();

        return ResponseEntity.ok(usuarios);
    }
}
