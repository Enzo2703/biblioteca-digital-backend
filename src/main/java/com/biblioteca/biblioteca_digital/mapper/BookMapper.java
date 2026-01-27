package com.biblioteca.biblioteca_digital.mapper;

import com.biblioteca.biblioteca_digital.model.Book;
import com.biblioteca.biblioteca_digital.dto.BookDTO;

public class BookMapper {

    // Convierte Book a BookDTO
    public static BookDTO toDTO(Book book) {
        if (book == null) return null;

        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setIsbn(book.getIsbn());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setAvailableCopies(book.getAvailableCopies());
        dto.setTotalCopies(book.getTotalCopies());
        dto.setCategory(book.getCategory());
        dto.setAuthorId(book.getAuthor() != null ? book.getAuthor().getId() : null);

        return dto;
    }

    // Convierte BookDTO a Book
    public static Book toEntity(BookDTO dto) {
        if (dto == null) return null;

        Book book = new Book();
        book.setId(dto.getId());
        book.setIsbn(dto.getIsbn());
        book.setTitle(dto.getTitle());
        book.setDescription(dto.getDescription());
        book.setPublicationYear(dto.getPublicationYear());
        book.setAvailableCopies(dto.getAvailableCopies());
        book.setTotalCopies(dto.getTotalCopies());
        book.setCategory(dto.getCategory());

        // Solo seteamos authorId si es necesario (debes cargar el Author por servicio)
        // book.setAuthor(authorService.findById(dto.getAuthorId()));

        return book;
    }
}
