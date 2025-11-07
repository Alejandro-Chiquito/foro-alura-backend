package com.alura.foro_backend.enums;

/**
 * Enum que define los posibles estados de un tópico
 */
public enum StatusTopico {
    ABIERTO("Abierto"),
    SOLUCIONADO("Solucionado"),
    CERRADO("Cerrado");

    private final String descripcion;

    StatusTopico(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
