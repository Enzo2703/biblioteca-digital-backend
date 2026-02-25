package com.biblioteca.biblioteca_digital.serviceImplement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca_digital.dto.DashboardResponse;
import com.biblioteca.biblioteca_digital.dto.UsuarioResponse;
import com.biblioteca.biblioteca_digital.enums.EstadoSuscripcion;
import com.biblioteca.biblioteca_digital.enums.Role;
import com.biblioteca.biblioteca_digital.model.Usuario;
import com.biblioteca.biblioteca_digital.repository.SuscripcionRepository;
import com.biblioteca.biblioteca_digital.repository.UsuarioRepository;
import com.biblioteca.biblioteca_digital.service.AdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImplement implements AdminService {
	
	private final UsuarioRepository usuarioRepository;
	private final SuscripcionRepository suscripcionRepository;
	
	@Override
	public List<UsuarioResponse> listarUsuarios() {
		return usuarioRepository.findAll().stream()
                .map(u -> new UsuarioResponse(
                        u.getId(),
                        u.getNombre(),
                        u.getEmail(),
                        u.getEstado(),
                        u.getRole() //agregado
                        
                ))
                .collect(Collectors.toList());
	}

	@Override
	public void cambiarRol(Integer id, Role role) {
		Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setRole(role);
        usuarioRepository.save(usuario);
		
	}

	@Override
	public void cambiarEstado(Integer id, String estado) {
		Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setEstado(estado);
        usuarioRepository.save(usuario);
		
	}

	@Override
	public DashboardResponse obtenerMetricas() {
		long totalUsuarios = usuarioRepository.count();
        long activos = usuarioRepository.countByEstado("A");
        long inactivos = usuarioRepository.countByEstado("I");

        long admins = usuarioRepository.countByRole(Role.ADMIN);
        long librarians = usuarioRepository.countByRole(Role.LIBRARIAN);
        long readers = usuarioRepository.countByRole(Role.READER);

        long totalSubs = suscripcionRepository.count();
        long subsActivas = suscripcionRepository.countByEstado(EstadoSuscripcion.ACTIVA);
        long subsVencidas = suscripcionRepository.countByEstado(EstadoSuscripcion.VENCIDA);

        return new DashboardResponse(
                totalUsuarios,
                activos,
                inactivos,
                admins,
                librarians,
                readers,
                totalSubs,
                subsActivas,
                subsVencidas
        );
	}

}
