package com.alura.foro_backend.entity;

import com.alura.foro_backend.enums.StatusTopico;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entidad que representa un tópico en el foro de Alura.
 * Un tópico es una pregunta o duda que un estudiante publica sobre un curso específico.
 */
@Entity(name = "Topico")
@Table(name = "topicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El mensaje no puede estar vacío")
    @Size(min = 10, max = 1000, message = "El mensaje debe tener entre 10 y 1000 caracteres")
    @Column(name = "mensaje", columnDefinition = "TEXT", nullable = false)
    private String mensaje;
    
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @NotNull(message = "El status es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "status_actual", nullable = false)
    private StatusTopico statusActual = StatusTopico.ABIERTO;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    @NotNull(message = "El autorResponseDTO es obligatorio")
    private Usuario autor;
    
    @NotBlank(message = "El curso es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre del curso debe tener entre 2 y 100 caracteres")
    @Column(name = "curso", nullable = false, length = 100)
    private String curso;

}
