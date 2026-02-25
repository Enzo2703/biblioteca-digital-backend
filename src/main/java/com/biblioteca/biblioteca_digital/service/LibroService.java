package com.biblioteca.biblioteca_digital.service;

import java.util.List;

import com.biblioteca.biblioteca_digital.model.Libro;

public interface LibroService {

	List<Libro> listar();

    Libro obtener(Integer id);

    Libro crear(Libro libro, String emailUsuario);

    Libro actualizar(Integer id, Libro libroActualizado);

    void eliminar(Integer id);

    List<Libro> buscar(String titulo);
}
