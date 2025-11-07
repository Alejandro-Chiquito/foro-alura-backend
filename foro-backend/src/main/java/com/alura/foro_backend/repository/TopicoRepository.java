package com.alura.foro_backend.repository;

import com.alura.foro_backend.entity.Topico;
import com.alura.foro_backend.enums.StatusTopico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repositorio para la gestión de tópicos.
 * Extiende JpaRepository para operaciones CRUD básicas y define métodos de búsqueda personalizados.
 * Sigue el patrón Repository para encapsular la lógica de acceso a datos.
 */
@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    
    /**
     * Busca tópicos por curso (búsqueda insensible a mayúsculas).
     * 
     * @param curso nombre del curso a buscar
     * @return página de tópicos del curso
     */
    Page<Topico> findByCursoContainingIgnoreCase(String curso, Pageable pageable);
    
    /**
     * Busca tópicos por estado.
     * 
     * @param status estado del tópico
     * @return página de tópicos con el estado especificado
     */
    Page<Topico> findByStatusActual(StatusTopico status, Pageable pageable);
    
    /**
     * Cuenta tópicos por estado.
     * 
     * @param status estado del tópico
     * @return cantidad de tópicos con el estado especificado
     */
    long countByStatusActual(StatusTopico status);


}
