package com.biblioteca.biblioteca_digital.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca_digital.dto.AuthResponse;
import com.biblioteca.biblioteca_digital.dto.ForgotPasswordRequest;
import com.biblioteca.biblioteca_digital.dto.LoginRequest;
import com.biblioteca.biblioteca_digital.dto.RegisterRequest;
import com.biblioteca.biblioteca_digital.model.TokenRecuperacion;
import com.biblioteca.biblioteca_digital.model.Usuario;
import com.biblioteca.biblioteca_digital.repository.TokenRecuperacionRepository;
import com.biblioteca.biblioteca_digital.repository.UsuarioRepository;
import com.biblioteca.biblioteca_digital.serviceImplement.EmailSenderServiceImplement;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    private final TokenRecuperacionRepository tokenRecuperacionRepository;
    private final EmailSenderServiceImplement emailSenderService;

    
    
    public AuthResponse register(RegisterRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        usuarioRepository.save(usuario);

        String jwtToken = jwtService.generateToken(usuario.getEmail());
        return new AuthResponse(jwtToken);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String jwtToken = jwtService.generateToken(usuario.getEmail());
        return new AuthResponse(jwtToken);
    }
    
    public void forgotPassword(ForgotPasswordRequest request) {
        usuarioRepository.findByEmail(request.getEmail()).ifPresent(usuario -> {
            String token = UUID.randomUUID().toString();
            
            TokenRecuperacion recuperacion = new TokenRecuperacion();
            recuperacion.setUsuario(usuario);
            recuperacion.setToken(token);
            recuperacion.setExpiracion(LocalDateTime.now().plusMinutes(30));
            tokenRecuperacionRepository.save(recuperacion);

            String link = "http://localhost:4200/reset-password/" + token;
            String mensaje = "Hola " + usuario.getNombre() + ",\n\nHaz clic en el siguiente enlace para restablecer tu contraseña:\n" + link + "\n\nEste enlace expirará en 30 minutos.";

            emailSenderService.enviarCorreo(usuario.getEmail(), "Recuperación de contraseña", mensaje);
        });
    }
    
    public void restablecerContrasena(String token, String nuevaContrasena) {
    	TokenRecuperacion tokenRec = tokenRecuperacionRepository.findByToken(token);

    	if (tokenRec == null || tokenRec.isUsado() || tokenRec.getExpiracion().isBefore(LocalDateTime.now())) {
    	    throw new RuntimeException("Token inválido.");
    	}

    if (tokenRec.isUsado()) {
        throw new RuntimeException("Este token ya fue utilizado.");
    }

    if (tokenRec.getExpiracion().isBefore(LocalDateTime.now())) {
        throw new RuntimeException("El token ha expirado.");
    }

    	Usuario usuario = tokenRec.getUsuario();
    	usuario.setPassword(passwordEncoder.encode(nuevaContrasena));
    	usuarioRepository.save(usuario);

    	tokenRec.setUsado(true);
    	tokenRecuperacionRepository.save(tokenRec);
    	tokenRecuperacionRepository.invalidarTokensActivos(usuario); // Invalida otros tokens antes de guardar el nuevo como usado
    	}

}
