package com.biblioteca.biblioteca_digital.serviceImplement;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca_digital.dto.HistorialResponseDto;
import com.biblioteca.biblioteca_digital.model.HistorialLectura;
import com.biblioteca.biblioteca_digital.model.Libro;
import com.biblioteca.biblioteca_digital.model.Usuario;
import com.biblioteca.biblioteca_digital.repository.HistorialLecturaRepository;
import com.biblioteca.biblioteca_digital.repository.LibroRepository;
import com.biblioteca.biblioteca_digital.repository.UsuarioRepository;
import com.biblioteca.biblioteca_digital.service.HistorialService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistorialServiceImpl implements HistorialService{

	private final HistorialLecturaRepository historialRepo;
    private final LibroRepository libroRepo;
    private final UsuarioRepository usuarioRepo;
    
	@Override
	public void registrarLectura(Integer libroId, Integer usuarioId) {
		
		Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Libro libro = libroRepo.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        HistorialLectura historial = HistorialLectura.builder()
                .usuario(usuario)
                .libro(libro)
                .fechaAcceso(LocalDateTime.now())
                .build();

        historialRepo.save(historial);
		
	}
	@Override
	public List<HistorialResponseDto> obtenerMiHistorial(Integer usuarioId) {
		
		return historialRepo.findByUsuarioIdOrderByFechaAccesoDesc(usuarioId)
                .stream()
                .map(h -> HistorialResponseDto.builder()
                        .id(h.getId())
                        .libroId(h.getLibro().getId())
                        .titulo(h.getLibro().getTitulo())
                        .autor(h.getLibro().getAutor())
                        .fechaAcceso(h.getFechaAcceso())
                        .build())
                .toList();
	}
}
