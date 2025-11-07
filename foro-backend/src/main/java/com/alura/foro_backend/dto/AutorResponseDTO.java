package com.alura.foro_backend.dto;

import com.alura.foro_backend.entity.Usuario;

public record AutorResponseDTO(
        Long id,
        String nombreUsuario
) {
    public AutorResponseDTO (Usuario usuario) {
        this(usuario.getId(), usuario.getNombreUsuario());
    }
}
