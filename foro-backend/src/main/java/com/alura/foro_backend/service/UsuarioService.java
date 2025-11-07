package com.alura.foro_backend.service;

import com.alura.foro_backend.entity.Usuario;
import com.alura.foro_backend.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> allUsers() {
        return new ArrayList<>(usuarioRepository.findAll());
    }
}
