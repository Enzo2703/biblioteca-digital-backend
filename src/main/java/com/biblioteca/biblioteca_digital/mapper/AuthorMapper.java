package com.biblioteca.biblioteca_digital.mapper;

import com.biblioteca.biblioteca_digital.model.Author;
import com.biblioteca.biblioteca_digital.dto.AuthorDTO;

public class AuthorMapper {

    public static AuthorDTO toDTO(Author author) {
        if (author == null) return null;

        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setFirstName(author.getFirstName());
        dto.setLastName(author.getLastName());
        dto.setBiography(author.getBiography());
        dto.setNationality(author.getNationality());
        dto.setBirthDate(author.getBirthDate());
        return dto;
    }

    public static Author toEntity(AuthorDTO dto) {
        if (dto == null) return null;

        Author author = new Author();
        author.setId(dto.getId());
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        author.setBiography(dto.getBiography());
        author.setNationality(dto.getNationality());
        author.setBirthDate(dto.getBirthDate());
        return author;
    }
}
