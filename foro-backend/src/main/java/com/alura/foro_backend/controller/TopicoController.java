package com.alura.foro_backend.controller;

import com.alura.foro_backend.dto.TopicoRequest;
import com.alura.foro_backend.dto.TopicoResponseDTO;
import com.alura.foro_backend.enums.StatusTopico;
import com.alura.foro_backend.service.TopicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controlador REST para la gestión de tópicos.
 * Proporciona endpoints para operaciones CRUD y búsquedas avanzadas.
 * Sigue el patrón REST Controller para exponer servicios como API REST.
 */
@RestController
@RequestMapping("/api/topicos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Tópicos", description = "API para la gestión de tópicos del foro de Alura")
public class TopicoController {

    private final TopicoService topicoService;

    /**
     * Crea un nuevo tópico.
     *
     * @param request datos del tópico a crear
     * @return tópico creado con código 201
     */
    @Operation(
        summary = "Crear tópico",
        description = "Crea un nuevo tópico en el foro con los datos proporcionados"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tópico creado exitosamente",
                    content = @Content(schema = @Schema(implementation = TopicoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<TopicoResponseDTO> crearTopico(@Valid @RequestBody TopicoRequest request,
                                                         Authentication authentication,
                                                         UriComponentsBuilder uriComponentsBuilder) {
        log.info("Creando nuevo tópico: {}", request);

        String autorEmail = authentication.getName();

        TopicoResponseDTO topico = topicoService.crearTopico(request, autorEmail);

        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.id()).toUri();
        return ResponseEntity.created(uri).body(topico);
    }

    /**
     * Obtiene un tópico por su ID.
     *
     * @param id ID del tópico
     * @return tópico encontrado
     */
    @Operation(
        summary = "Obtener tópico por ID",
        description = "Obtiene un tópico específico por su identificador único"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tópico encontrado exitosamente",
                    content = @Content(schema = @Schema(implementation = TopicoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Tópico no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> obtenerTopicoPorId(
            @Parameter(description = "ID del tópico a obtener") @PathVariable Long id) {
        log.info("Obteniendo tópico con ID: {}", id);
        TopicoResponseDTO topico = topicoService.obtenerTopicoPorId(id);
        return ResponseEntity.ok(topico);
    }

    /**
     * Obtiene todos los tópicos con paginación.
     *
     * @param pageable configuración de paginación
     * @return página de tópicos
     */
    @GetMapping
    public ResponseEntity<Page<TopicoResponseDTO>> obtenerTodosLosTopicos(@PageableDefault(size = 10,
            sort = "fechaCreacion") Pageable pageable) {
        log.info("Obteniendo todos los tópicos con paginación: {}", pageable);
        Page<TopicoResponseDTO> topicos = topicoService.obtenerTodosLosTopicos(pageable);
        return ResponseEntity.ok(topicos);
    }

    /**
     * Obtiene todos los tópicos sin paginación.
     *
     * @return lista de todos los tópicos
     */
    @GetMapping("/todos")
    public ResponseEntity<List<TopicoResponseDTO>> obtenerTodosLosTopicos() {
        log.info("Obteniendo todos los tópicos sin paginación");
        List<TopicoResponseDTO> topicos = topicoService.obtenerTodosLosTopicos();
        return ResponseEntity.ok(topicos);
    }

    /**
     * Actualiza un tópico existente.
     *
     * @param id ID del tópico a actualizar
     * @param request nuevos datos del tópico
     * @return tópico actualizado
     */
    @PatchMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> actualizarTopico(
            @PathVariable Long id,
            @Valid @RequestBody TopicoRequest request) {
        log.info("Actualizando tópico {} con datos: {}", id, request);
        TopicoResponseDTO topico = topicoService.actualizarTopico(id, request);
        return ResponseEntity.ok(topico);
    }

    /**
     * Actualiza solo el estado de un tópico.
     *
     * @param id ID del tópico
     * @param status nuevo estado del tópico
     * @return tópico actualizado
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<TopicoResponseDTO> actualizarStatusTopico(
            @PathVariable Long id,
            @RequestParam StatusTopico status) {
        log.info("Actualizando status del tópico {} a {}", id, status);
        TopicoResponseDTO topico = topicoService.actualizarStatusTopico(id, status);
        return ResponseEntity.ok(topico);
    }

    /**
     * Elimina un tópico por su ID.
     *
     * @param id ID del tópico a eliminar
     * @return respuesta sin contenido (204)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        log.info("Eliminando tópico con ID: {}", id);
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Busca tópicos por curso.
     *
     * @param curso nombre del curso
     * @return lista de tópicos del curso
     */
    @GetMapping("/buscar/curso")
    public ResponseEntity<Page<TopicoResponseDTO>> buscarPorCurso(@RequestParam String curso, @PageableDefault(size = 10) Pageable pageable) {
        log.info("Buscando tópicos por curso: {}", curso);
        Page<TopicoResponseDTO> topicos = topicoService.buscarPorCurso(curso, pageable);
        return ResponseEntity.ok(topicos);
    }

    /**
     * Busca tópicos por estado.
     *
     * @param status estado del tópico
     * @return lista de tópicos con el estado especificado
     */
    @GetMapping("/buscar/status")
    public ResponseEntity<Page<TopicoResponseDTO>> buscarPorStatus(@RequestParam StatusTopico status, @PageableDefault(size = 10) Pageable pageable) {
        log.info("Buscando tópicos por status: {}", status);
        Page<TopicoResponseDTO> topicos = topicoService.buscarPorStatus(status, pageable);
        return ResponseEntity.ok(topicos);
    }

}
