package com.alura.foro_backend.entity;

import com.alura.foro_backend.dto.SignupUserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Entidad de usuario básica para autenticación.
 * Contiene "usuario" (nombre de usuario), "email" y "password" (password hash).
 */
@Entity(name = "Usuario")
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "El email es obligatorio")
	@Size(max = 255, message = "El email debe tener máximo 255 caracteres")
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@NotBlank(message = "El usuario es obligatorio")
	@Size(min = 3, max = 100, message = "El usuario debe tener entre 3 y 100 caracteres")
	@Column(name = "nombre_usuario", nullable = false, unique = true, length = 100)
	private String nombreUsuario;

	@NotBlank(message = "La contraseña es obligatoria")
	@Size(min = 6, max = 255, message = "La contraseña debe tener al menos 6 caracteres")
	@Column(name = "password", nullable = false)
	private String password;

	public Usuario(SignupUserDTO signupUserDTO) {
		this.email = signupUserDTO.email();
		this.nombreUsuario = signupUserDTO.nombreUsuario();
		this.password = signupUserDTO.password();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return UserDetails.super.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return UserDetails.super.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return UserDetails.super.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return UserDetails.super.isEnabled();
	}
}



