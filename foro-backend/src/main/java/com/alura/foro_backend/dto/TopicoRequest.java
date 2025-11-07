package com.alura.foro_backend.dto;

import com.alura.foro_backend.enums.StatusTopico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para las peticiones de creación y actualización de tópicos.
 * Utiliza record para inmutabilidad y simplicidad.
 * 
 * @param mensaje El contenido del mensaje del tópico
 * @param statusActual El estado actual del tópico
 * @param curso El nombre del curso relacionado
 */
public record TopicoRequest(
    @NotBlank(message = "El mensaje no puede estar vacío")
    @Size(min = 10, max = 1000, message = "El mensaje debe tener entre 10 y 1000 caracteres")
    String mensaje,
    
    @NotNull(message = "El status es obligatorio")
    StatusTopico statusActual,
    
    @NotBlank(message = "El curso es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre del curso debe tener entre 2 y 100 caracteres")
    String curso
) {
    /**
     * Constructor por defecto que establece el status como ABIERTO si no se especifica.
     */
    public TopicoRequest {
        if (statusActual == null) {
            statusActual = StatusTopico.ABIERTO;
        }
    }
}
