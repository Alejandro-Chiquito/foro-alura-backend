package com.alura.foro_backend.dto;

public record LoginUserResponseDTO(String token, long expiresIn) {
}
