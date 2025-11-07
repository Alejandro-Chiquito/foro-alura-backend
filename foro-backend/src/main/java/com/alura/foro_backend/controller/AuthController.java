package com.alura.foro_backend.controller;

import com.alura.foro_backend.dto.LoginUserDTO;
import com.alura.foro_backend.dto.LoginUserResponseDTO;
import com.alura.foro_backend.dto.SignupUserDTO;
import com.alura.foro_backend.dto.UsuarioResponseDTO;
import com.alura.foro_backend.entity.Usuario;
import com.alura.foro_backend.service.AuthService;
import com.alura.foro_backend.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<UsuarioResponseDTO> register(@RequestBody SignupUserDTO signupUserDTO, UriComponentsBuilder uriComponentsBuilder) {
        Usuario usuarioRegistrado = authenticationService.registrar(signupUserDTO);

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(usuarioRegistrado);

        URI uri = uriComponentsBuilder.path("/usuarios/{id}")
                .buildAndExpand(usuarioResponseDTO.id()).toUri();

        return ResponseEntity.created(uri).body(usuarioResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDTO> authenticate(@RequestBody LoginUserDTO loginUserDTO) {
        Usuario usuarioAutenticado = authenticationService.autenticar(loginUserDTO);

        String jwtToken = jwtService.generateToken(usuarioAutenticado);

        LoginUserResponseDTO loginUserResponseDTO = new LoginUserResponseDTO(jwtToken, jwtService.getExpirationTime());

        return ResponseEntity.ok(loginUserResponseDTO);
    }

}
