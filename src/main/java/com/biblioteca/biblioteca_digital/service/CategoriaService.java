package com.biblioteca.biblioteca_digital.service;

import java.util.List;

import com.biblioteca.biblioteca_digital.model.Categoria;

public interface CategoriaService {

	List<Categoria> listar();

    Categoria obtener(Integer id);

    Categoria crear(Categoria categoria);

    Categoria actualizar(Integer id, Categoria categoriaActualizada);

    void eliminar(Integer id); // soft delete
}
