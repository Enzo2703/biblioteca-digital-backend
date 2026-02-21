package com.biblioteca.biblioteca_digital.mapper;

import com.biblioteca.biblioteca_digital.model.Book;
import com.biblioteca.biblioteca_digital.dto.BookDTO;

public class BookMapper {

    // Book -> BookDTO
    public static BookDTO toDTO(Book book) {
        if (book == null) return null;

        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitulo(book.getTitulo());
        dto.setAutor(book.getAutor());
        dto.setDescripcion(book.getDescripcion());
        dto.setTipo(book.getTipo());
        dto.setPortadaUrl(book.getPortadaUrl());
        dto.setArchivoUrl(book.getArchivoUrl());
        dto.setCategoriaId(book.getCategoriaId());
        dto.setCreadoPorId(book.getCreadoPor());
        dto.setEstado(book.getEstado());

        return dto;
    }

    // BookDTO -> Book
    public static Book toEntity(BookDTO dto) {
        if (dto == null) return null;

        Book book = new Book();
        book.setId(dto.getId());
        book.setTitulo(dto.getTitulo());
        book.setAutor(dto.getAutor());
        book.setDescripcion(dto.getDescripcion());
        book.setTipo(dto.getTipo());
        book.setPortadaUrl(dto.getPortadaUrl());
        book.setArchivoUrl(dto.getArchivoUrl());
        book.setCategoriaId(dto.getCategoriaId());
        book.setCreadoPor(dto.getCreadoPorId());
        book.setEstado(dto.getEstado());

        return book;
    }
}