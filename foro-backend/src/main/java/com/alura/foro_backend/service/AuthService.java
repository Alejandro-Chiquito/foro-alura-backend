package com.alura.foro_backend.service;

import com.alura.foro_backend.dto.LoginUserDTO;
import com.alura.foro_backend.dto.SignupUserDTO;
import com.alura.foro_backend.entity.Usuario;
import com.alura.foro_backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public Usuario registrar(SignupUserDTO input) {
        Usuario usuario = new Usuario();

        usuario.setNombreUsuario(input.nombreUsuario());
        usuario.setEmail(input.email());
        usuario.setPassword(passwordEncoder.encode(input.password()));

        return usuarioRepository.save(usuario);
    }

    public Usuario autenticar(LoginUserDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.email(),
                        input.password()
                )
        );

        return usuarioRepository.findUserByEmail(input.email())
                .orElseThrow();
    }

}
