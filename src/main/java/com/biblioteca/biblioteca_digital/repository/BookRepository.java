package com.biblioteca.biblioteca_digital.repository;

import com.biblioteca.biblioteca_digital.enums.Category;
import com.biblioteca.biblioteca_digital.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByCategory(Category category);
    List<Book> findByTitleContainingIgnoreCaseOrIsbnContainingIgnoreCase(
            String title,
            String isbn
    
    		);
}
