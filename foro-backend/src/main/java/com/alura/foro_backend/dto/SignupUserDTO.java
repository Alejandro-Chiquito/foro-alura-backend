package com.alura.foro_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignupUserDTO(
        @NotNull
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(min = 2, max = 100)
        String email,
        @NotNull
        @NotBlank(message = "La contraseña no puede estar vacía")
        @Size(min = 8, max = 100)
        String password,
        @NotNull
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(min = 2, max = 100)
        String nombreUsuario) {
}
