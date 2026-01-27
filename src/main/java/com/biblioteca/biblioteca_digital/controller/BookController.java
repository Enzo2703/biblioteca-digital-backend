package com.biblioteca.biblioteca_digital.controller;

import com.biblioteca.biblioteca_digital.dto.BookDTO;
import com.biblioteca.biblioteca_digital.mapper.BookMapper;
import com.biblioteca.biblioteca_digital.model.Book;
import com.biblioteca.biblioteca_digital.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Listar todos los libros
    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks()
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }

 // 🔍 Búsqueda tipo barra
    @GetMapping("/search")
    public List<BookDTO> searchBooks(@RequestParam String query) {
        return bookService.searchBooks(query)
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }

    
    
    // Obtener libro por id
    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return BookMapper.toDTO(book);
    }

    // Crear nuevo libro
    @PostMapping
    public BookDTO createBook(@RequestBody Book book) {
        Book created = bookService.createBook(book);
        return BookMapper.toDTO(created);
    }

    // Actualizar libro
    @PutMapping("/{id}")
    public BookDTO updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updated = bookService.updateBook(id, book);
        return BookMapper.toDTO(updated);
    }

    // Eliminar libro
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "Libro eliminado exitosamente";
    }
}
