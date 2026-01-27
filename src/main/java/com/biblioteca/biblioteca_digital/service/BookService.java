package com.biblioteca.biblioteca_digital.service;

import com.biblioteca.biblioteca_digital.exception.ResourceNotFoundException;
import com.biblioteca.biblioteca_digital.model.Book;
import com.biblioteca.biblioteca_digital.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con id: " + id));
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);
        book.setIsbn(bookDetails.getIsbn());
        book.setTitle(bookDetails.getTitle());
        book.setDescription(bookDetails.getDescription());
        book.setPublicationYear(bookDetails.getPublicationYear());
        book.setAvailableCopies(bookDetails.getAvailableCopies());
        book.setTotalCopies(bookDetails.getTotalCopies());
        book.setCategory(bookDetails.getCategory());
        book.setAuthor(bookDetails.getAuthor());
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }
    
    public List<Book> searchBooks(String query) {
        return bookRepository
                .findByTitleContainingIgnoreCaseOrIsbnContainingIgnoreCase(query, query);
    }

}

