package com.biblioteca.biblioteca_digital.serviceImplement;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca_digital.model.Categoria;
import com.biblioteca.biblioteca_digital.repository.CategoriaRepository;
import com.biblioteca.biblioteca_digital.service.CategoriaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImplement implements CategoriaService {

	private final CategoriaRepository categoriaRepository;
	
	@Override
	public List<Categoria> listar() {
		return categoriaRepository.findByEstado("A");
	}

	@Override
	public Categoria obtener(Integer id) {
		 return categoriaRepository.findById(id)
	                .filter(c -> c.getEstado().equals("A"))
	                .orElseThrow(() -> new RuntimeException("Categoría no encontrada o inactiva"));
	}

	@Override
	public Categoria crear(Categoria categoria) {
		// Validar nombre duplicado
		if (categoriaRepository.findByNombreIgnoreCase(categoria.getNombre()).isPresent()) {
	        throw new RuntimeException("Ya existe una categoría con ese nombre");
	    }

	    categoria.setEstado("A");
	    return categoriaRepository.save(categoria);
	}

	@Override
	public Categoria actualizar(Integer id, Categoria categoriaActualizada) {
		Categoria categoria = obtener(id);

	    Optional<Categoria> existente =
	            categoriaRepository.findByNombreIgnoreCase(categoriaActualizada.getNombre());

	    if (existente.isPresent() && !existente.get().getId().equals(id)) {
	        throw new RuntimeException("Ya existe otra categoría con ese nombre");
	    }

	    categoria.setNombre(categoriaActualizada.getNombre());
	    categoria.setDescripcion(categoriaActualizada.getDescripcion());

	    return categoriaRepository.save(categoria);
	}

	@Override
	public void eliminar(Integer id) {
		Categoria categoria = obtener(id);

        // Soft delete
        categoria.setEstado("I");

        categoriaRepository.save(categoria);
		
	}

	

}
