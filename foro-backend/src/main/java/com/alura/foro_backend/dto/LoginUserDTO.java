package com.alura.foro_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginUserDTO(
        @NotNull
        @NotBlank(message = "El email no puede estar vacío")
        String email,
        @NotNull
        @NotBlank(message = "La contraseña no puede estar vacía")
        String password) {
}
