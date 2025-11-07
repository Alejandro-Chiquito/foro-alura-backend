package com.alura.foro_backend.exception;

/**
 * Excepción personalizada que se lanza cuando no se encuentra un recurso específico.
 * Extiende RuntimeException para ser una excepción no verificada.
 * Sigue el patrón de excepciones específicas del dominio.
 */
public class ResourceNotFoundException extends RuntimeException {
    
    /**
     * Constructor que acepta un mensaje de error.
     * 
     * @param message mensaje descriptivo del error
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructor que acepta un mensaje y una causa.
     * 
     * @param message mensaje descriptivo del error
     * @param cause causa original de la excepción
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
