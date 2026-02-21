package com.biblioteca.biblioteca_digital.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.biblioteca.biblioteca_digital.dto.BookDTO;
import com.biblioteca.biblioteca_digital.mapper.BookMapper;
import com.biblioteca.biblioteca_digital.model.Book;
import com.biblioteca.biblioteca_digital.service.BookService;

@RestController
@RequestMapping("/api/libros")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks()
                .stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable Long id) {
        return BookMapper.toDTO(bookService.getBookById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    public BookDTO createBook(@RequestBody BookDTO bookDTO) {
        Book book = BookMapper.toEntity(bookDTO);
        // Aquí se puede setear creadoPorId desde el usuario logueado
        // book.setCreadoPor(loggedUserId);
        return BookMapper.toDTO(bookService.createBook(book));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    public BookDTO updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        Book book = BookMapper.toEntity(bookDTO);
        return BookMapper.toDTO(bookService.updateBook(id, book));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/search")
    public List<BookDTO> searchBooks(@RequestParam String q) {
        return bookService.searchBooks(q)
                .stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }
}