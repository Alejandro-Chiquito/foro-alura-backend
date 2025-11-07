package com.alura.foro_backend.dto;

import com.alura.foro_backend.entity.Topico;
import com.alura.foro_backend.enums.StatusTopico;

import java.time.LocalDateTime;

/**
 * DTO para las respuestas de tópicos.
 * Utiliza record para inmutabilidad y simplicidad.
 * 
 * @param id Identificador único del tópico
 * @param mensaje El contenido del mensaje del tópico
 * @param fechaCreacion Fecha y hora de creación del tópico
 * @param statusActual El estado actual del tópico
 * @param autorResponseDTO El nombre del autorResponseDTO del tópico
 * @param curso El nombre del curso relacionado
 */
public record TopicoResponseDTO(
        Long id,
        String mensaje,
        LocalDateTime fechaCreacion,
        StatusTopico statusActual,
        AutorResponseDTO autorResponseDTO,
        String curso
) {
    /**
     * Método factory para crear TopicoResponse desde una entidad Topico.
     * Sigue el patrón Factory Method para la conversión de entidades a DTOs.
     * 
     * @param topico La entidad Topico a convertir
     * @return TopicoResponse creado desde la entidad
     */
    public static TopicoResponseDTO fromEntity(Topico topico) {
        return new TopicoResponseDTO(
            topico.getId(),
            topico.getMensaje(),
            topico.getFechaCreacion(),
            topico.getStatusActual(),
            new AutorResponseDTO(topico.getAutor()),
            topico.getCurso()
        );
    }
}
