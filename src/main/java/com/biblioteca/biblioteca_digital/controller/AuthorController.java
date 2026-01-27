package com.biblioteca.biblioteca_digital.controller;

import com.biblioteca.biblioteca_digital.dto.AuthorDTO;
import com.biblioteca.biblioteca_digital.mapper.AuthorMapper;
import com.biblioteca.biblioteca_digital.model.Author;
import com.biblioteca.biblioteca_digital.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<AuthorDTO> getAll() {
        return authorService.getAllAuthors()
                            .stream()
                            .map(AuthorMapper::toDTO)
                            .toList();
    }

    @GetMapping("/{id}")
    public AuthorDTO getById(@PathVariable Long id) {
        return AuthorMapper.toDTO(authorService.getAuthorById(id));
    }

    @PostMapping
    public AuthorDTO create(@RequestBody AuthorDTO authorDTO) {
        Author author = AuthorMapper.toEntity(authorDTO);
        return AuthorMapper.toDTO(authorService.createAuthor(author));
    }

    @PutMapping("/{id}")
    public AuthorDTO update(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        Author author = AuthorMapper.toEntity(authorDTO);
        return AuthorMapper.toDTO(authorService.updateAuthor(id, author));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "Autor eliminado exitosamente";
    }
}
