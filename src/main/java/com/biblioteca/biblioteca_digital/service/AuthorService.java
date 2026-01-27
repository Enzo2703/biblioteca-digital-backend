package com.biblioteca.biblioteca_digital.service;

import com.biblioteca.biblioteca_digital.exception.ResourceNotFoundException;
import com.biblioteca.biblioteca_digital.model.Author;
import com.biblioteca.biblioteca_digital.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado con id: " + id));
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id, Author authorDetails) {
        Author author = getAuthorById(id);
        author.setFirstName(authorDetails.getFirstName());
        author.setLastName(authorDetails.getLastName());
        author.setBiography(authorDetails.getBiography());
        author.setNationality(authorDetails.getNationality());
        author.setBirthDate(authorDetails.getBirthDate());
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        Author author = getAuthorById(id);
        authorRepository.delete(author);
    }
}

