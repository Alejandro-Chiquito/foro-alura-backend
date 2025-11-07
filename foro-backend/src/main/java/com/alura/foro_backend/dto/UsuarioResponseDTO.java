package com.alura.foro_backend.dto;

import com.alura.foro_backend.entity.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String email,
        String nombreUsuario
) {
    public UsuarioResponseDTO (Usuario usuario) {
        this(usuario.getId(), usuario.getEmail(), usuario.getNombreUsuario());
    }
}
