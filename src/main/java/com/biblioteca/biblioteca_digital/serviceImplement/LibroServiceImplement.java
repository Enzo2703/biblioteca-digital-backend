package com.biblioteca.biblioteca_digital.serviceImplement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca_digital.model.Categoria;
import com.biblioteca.biblioteca_digital.model.Libro;
import com.biblioteca.biblioteca_digital.model.Usuario;
import com.biblioteca.biblioteca_digital.repository.CategoriaRepository;
import com.biblioteca.biblioteca_digital.repository.LibroRepository;
import com.biblioteca.biblioteca_digital.repository.UsuarioRepository;
import com.biblioteca.biblioteca_digital.service.LibroService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LibroServiceImplement implements LibroService {
	
	private final LibroRepository libroRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;
    
	@Override
	public List<Libro> listar() {
		return libroRepository.findByEstado("A");
	}

	@Override
	public Libro obtener(Integer id) {
		return libroRepository.findByIdAndEstado(id, "A")
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
	}

	@Override
	public Libro crear(Libro libro, String emailUsuario) {
		Usuario creador = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Categoria categoria = categoriaRepository.findById(libro.getCategoria().getId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        libro.setCategoria(categoria);
        libro.setCreadoPor(creador);
        libro.setEstado("A");

        return libroRepository.save(libro);
	}

	@Override
	public Libro actualizar(Integer id, Libro libroActualizado) {
		Libro libro = obtener(id);

        libro.setTitulo(libroActualizado.getTitulo());
        libro.setAutor(libroActualizado.getAutor());
        libro.setDescripcion(libroActualizado.getDescripcion());
        libro.setTipo(libroActualizado.getTipo());
        libro.setPortadaUrl(libroActualizado.getPortadaUrl());
        libro.setArchivoUrl(libroActualizado.getArchivoUrl());

        return libroRepository.save(libro);
	}

	@Override
	public void eliminar(Integer id) {
		Libro libro = obtener(id);
        libro.setEstado("I");
        libroRepository.save(libro);
		
	}

	@Override
	public List<Libro> buscar(String titulo) {
		 return libroRepository.findByTituloContainingIgnoreCaseAndEstado(titulo, "A");
	}

	
	

}
