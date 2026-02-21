package com.biblioteca.biblioteca_digital.mapper;

import org.springframework.stereotype.Component;
import com.biblioteca.biblioteca_digital.model.Categoria;
import com.biblioteca.biblioteca_digital.dto.CategoriaDTO;

@Component
public class CategoriaMapper {

    public CategoriaDTO toDTO(Categoria categoria) {
        return new CategoriaDTO(
            categoria.getId(),
            categoria.getNombre(),
            categoria.getDescripcion(),
            categoria.getEstado()
        );
    }

    public Categoria toEntity(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setId(dto.getId());
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        categoria.setEstado(dto.getEstado() != null ? dto.getEstado() : "A"); // activo por defecto
        return categoria;
    }
}