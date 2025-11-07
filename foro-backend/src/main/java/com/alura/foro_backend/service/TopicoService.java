package com.alura.foro_backend.service;

import com.alura.foro_backend.dto.*;
import com.alura.foro_backend.entity.Topico;
import com.alura.foro_backend.enums.StatusTopico;
import com.alura.foro_backend.entity.Usuario;
import com.alura.foro_backend.exception.*;
import com.alura.foro_backend.repository.UsuarioRepository;
import com.alura.foro_backend.repository.TopicoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de tópicos.
 * Implementa la lógica de negocio y actúa como intermediario entre el controller y el repository.
 * Sigue el patrón Service Layer para encapsular la lógica de negocio.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TopicoService {
    
    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    
    /**
     * Crea un nuevo tópico.
     * @param request datos del tópico a crear
     * @param autorEmail el nombre de usuario del autorResponseDTO autenticado
     * @return tópico creado
     */
    public TopicoResponseDTO crearTopico(TopicoRequest request, String autorEmail) {
        log.info("Creando nuevo tópico para el curso: {} por el autor: {}", request.curso(), autorEmail);

        Usuario autor = usuarioRepository.findUserByEmail(autorEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario autorResponseDTO no encontrado: " + autorEmail));
        
        Topico topico = new Topico();
        topico.setMensaje(request.mensaje());
        // El status se setea por defecto en la entidad, no es necesario aquí
        // este problemita de seguridad se queda para otro momento
        topico.setAutor(autor);
        topico.setCurso(request.curso());

        
        Topico topicoGuardado = topicoRepository.save(topico);
        log.info("Tópico creado exitosamente con ID: {}", topicoGuardado.getId());
        
        return TopicoResponseDTO.fromEntity(topicoGuardado);
    }
    
    /**
     * Obtiene un tópico por su ID.
     * @param id ID del tópico
     * @return tópico encontrado
     * @throws RuntimeException si el tópico no existe
     */
    @Transactional(readOnly = true)
    public TopicoResponseDTO obtenerTopicoPorId(Long id) {
        log.info("Buscando tópico con ID: {}", id);
        
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado con ID: " + id));
        
        log.info("Tópico encontrado: {}", topico.getMensaje());
        return TopicoResponseDTO.fromEntity(topico);
    }
    
    /**
     * Obtiene todos los tópicos con paginación.
     * @param pageable configuración de paginación
     * @return página de tópicos
     */
    @Transactional(readOnly = true)
    public Page<TopicoResponseDTO> obtenerTodosLosTopicos(Pageable pageable) {
        log.info("Obteniendo todos los tópicos con paginación: {}", pageable);
        
        Page<Topico> topicos = topicoRepository.findAll(pageable);
        return topicos.map(TopicoResponseDTO::fromEntity);
    }
    
    /**
     * Obtiene todos los tópicos sin paginación.
     * @return lista de todos los tópicos
     */
    @Transactional(readOnly = true)
    public List<TopicoResponseDTO> obtenerTodosLosTopicos() {
        log.info("Obteniendo todos los tópicos");
        
        List<Topico> topicos = topicoRepository.findAll();
        return topicos.stream()
                .map(TopicoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * Actualiza un tópico existente.
     * @param id ID del tópico a actualizar
     * @param request nuevos datos del tópico
     * @return tópico actualizado
     * @throws RuntimeException si el tópico no existe
     */
    public TopicoResponseDTO actualizarTopico(Long id, TopicoRequest request) {
        log.info("Actualizando tópico con ID: {}", id);
        
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado con ID: " + id));
        
        topico.setMensaje(request.mensaje());
        topico.setStatusActual(request.statusActual());
        topico.setCurso(request.curso());
        
        Topico topicoActualizado = topicoRepository.save(topico);
        log.info("Tópico actualizado exitosamente con ID: {}", topicoActualizado.getId());
        
        return TopicoResponseDTO.fromEntity(topicoActualizado);
    }
    
    /**
     * Elimina un tópico por su ID.
     * @param id ID del tópico a eliminar
     * @throws RuntimeException si el tópico no existe
     */
    public void eliminarTopico(Long id) {
        log.info("Eliminando tópico con ID: {}", id);
        
        if (!topicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tópico no encontrado con ID: " + id);
        }
        
        topicoRepository.deleteById(id);
        log.info("Tópico eliminado exitosamente con ID: {}", id);
    }
    
    /**
     * Busca tópicos por curso.
     * @param curso nombre del curso
     * @return lista de tópicos del curso
     */
    @Transactional(readOnly = true)
    public Page<TopicoResponseDTO> buscarPorCurso(String curso, Pageable pageable) {
        log.info("Buscando tópicos por curso: {}", curso);
        
        Page<Topico> topicos = topicoRepository.findByCursoContainingIgnoreCase(curso, pageable);

        return topicos.map(TopicoResponseDTO::fromEntity);
    }
    
    /**
     * Busca tópicos por status.
     * @param status status del tópico
     * @return lista de tópicos con el status especificado
     */
    @Transactional(readOnly = true)
    public Page<TopicoResponseDTO> buscarPorStatus(StatusTopico status, Pageable pageable) {
        log.info("Buscando tópicos por status: {}", status);
        
        Page<Topico> topicos = topicoRepository.findByStatusActual(status, pageable);

        return topicos.map(TopicoResponseDTO::fromEntity);
    }
    
    /**
     * Actualiza solo el estado de un tópico.
     * @param id ID del tópico
     * @param nuevoStatus nuevo estado del tópico
     * @return tópico actualizado
     */
    public TopicoResponseDTO actualizarStatusTopico(Long id, StatusTopico nuevoStatus) {
        log.info("Actualizando status del tópico {} a {}", id, nuevoStatus);
        
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado con ID: " + id));

        topico.setStatusActual(nuevoStatus);
        Topico topicoActualizado = topicoRepository.save(topico);
        
        log.info("Status del tópico {} actualizado exitosamente a {}", id, nuevoStatus);
        return TopicoResponseDTO.fromEntity(topicoActualizado);
    }
}
