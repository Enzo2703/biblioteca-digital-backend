package com.biblioteca.biblioteca_digital.serviceImplement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca_digital.dto.FavoritoResponseDto;
import com.biblioteca.biblioteca_digital.model.Favorito;
import com.biblioteca.biblioteca_digital.model.Libro;
import com.biblioteca.biblioteca_digital.model.Usuario;
import com.biblioteca.biblioteca_digital.repository.FavoritoRepository;
import com.biblioteca.biblioteca_digital.repository.LibroRepository;
import com.biblioteca.biblioteca_digital.repository.UsuarioRepository;
import com.biblioteca.biblioteca_digital.service.FavoritoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoritoServiceImpl implements FavoritoService{

	private final FavoritoRepository favoritoRepo;
    private final LibroRepository libroRepo;
    private final UsuarioRepository usuarioRepo;
    
	@Override
	public void agregarFavorito(Integer libroId, Integer usuarioId) {
		if (favoritoRepo.findByUsuarioIdAndLibroId(usuarioId, libroId).isPresent()) {
            throw new RuntimeException("Ya está en favoritos");
        }

        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Libro libro = libroRepo.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        Favorito favorito = Favorito.builder()
                .usuario(usuario)
                .libro(libro)
                .build();

        favoritoRepo.save(favorito);
		
	}
	@Override
	public void eliminarFavorito(Integer favoritoId, Integer usuarioId) {
		 
		Favorito favorito = favoritoRepo.findById(favoritoId)
	                .orElseThrow(() -> new RuntimeException("Favorito no encontrado"));

	        if (!favorito.getUsuario().getId().equals(usuarioId)) {
	            throw new RuntimeException("No autorizado");
	        }

	        favoritoRepo.delete(favorito);
		
	}
	@Override
	public List<FavoritoResponseDto> listarMisFavoritos(Integer usuarioId) {
		
		return favoritoRepo.findByUsuarioId(usuarioId)
                .stream()
                .map(f -> FavoritoResponseDto.builder()
                        .id(f.getId())
                        .libroId(f.getLibro().getId())
                        .titulo(f.getLibro().getTitulo())
                        .autor(f.getLibro().getAutor())
                        .build())
                .toList();
	}
}
