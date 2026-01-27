package com.biblioteca.biblioteca_digital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.biblioteca_digital.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
