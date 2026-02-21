package com.biblioteca.biblioteca_digital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.biblioteca_digital.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Buscar libro activo por ID
    Optional<Book> findByIdAndEstado(Long id, String estado);

    // Obtener todos los libros activos
    List<Book> findByEstado(String estado);

    // Buscar libros activos por título o autor (filtro de búsqueda)
    List<Book> findByTituloContainingIgnoreCaseAndEstado(String titulo, String estado);
    List<Book> findByAutorContainingIgnoreCaseAndEstado(String autor, String estado);
    
    // Buscar por tipo y estado
    List<Book> findByTipoAndEstado(String tipo, String estado);
}