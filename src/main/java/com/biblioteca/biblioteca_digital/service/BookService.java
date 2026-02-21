package com.biblioteca.biblioteca_digital.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import com.biblioteca.biblioteca_digital.model.Book;
import com.biblioteca.biblioteca_digital.model.Categoria;
import com.biblioteca.biblioteca_digital.model.Usuario;
import com.biblioteca.biblioteca_digital.repository.BookRepository;
import com.biblioteca.biblioteca_digital.repository.CategoriaRepository;
import com.biblioteca.biblioteca_digital.repository.UsuarioRepository;
import com.biblioteca.biblioteca_digital.exception.ResourceNotFoundException;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public BookService(BookRepository bookRepository, CategoriaRepository categoriaRepository, UsuarioRepository usuarioRepository) {
        this.bookRepository = bookRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Obtener todos los libros activos
    public List<Book> getAllBooks() {
        return bookRepository.findByEstado("A");
    }

    // Obtener libro por ID activo
    public Book getBookById(Long id) {
        return bookRepository.findByIdAndEstado(id, "A")
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con id: " + id));
    }

    // Crear libro validando que la categoría exista y asignando creador
    public Book createBook(Book book) {
        // Verificar que la categoría exista
        Categoria categoria = categoriaRepository.findById(book.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoría no encontrada con id: " + book.getCategoriaId()));

        // Obtener el email del usuario autenticado desde Spring Security
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Buscar el usuario completo en la base de datos
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario no encontrado con email: " + email));

        // Asignar creador y estado del libro
        book.setCreadoPor(usuario.getId());
        book.setEstado("A"); // activo por defecto

        // Guardar y retornar el libro
        return bookRepository.save(book);
    }

    // Actualizar libro
    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);

        Categoria categoria = categoriaRepository.findById(bookDetails.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + bookDetails.getCategoriaId()));

        book.setTitulo(bookDetails.getTitulo());
        book.setAutor(bookDetails.getAutor());
        book.setDescripcion(bookDetails.getDescripcion());
        book.setTipo(bookDetails.getTipo());
        book.setPortadaUrl(bookDetails.getPortadaUrl());
        book.setArchivoUrl(bookDetails.getArchivoUrl());
        book.setCategoriaId(categoria.getId());

        return bookRepository.save(book);
    }

    // Soft delete
    public void deleteBook(Long id) {
        Book book = getBookById(id);
        book.setEstado("I"); // marcar como inactivo
        bookRepository.save(book);
    }

    // Filtro de búsqueda
    public List<Book> searchBooks(String query) {
        List<Book> byTitulo = bookRepository.findByTituloContainingIgnoreCaseAndEstado(query, "A");
        List<Book> byAutor = bookRepository.findByAutorContainingIgnoreCaseAndEstado(query, "A");
        byTitulo.addAll(byAutor); // combinar resultados
        return byTitulo;
    }
}