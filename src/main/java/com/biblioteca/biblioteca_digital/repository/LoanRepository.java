package com.biblioteca.biblioteca_digital.repository;

import com.biblioteca.biblioteca_digital.enums.LoanStatus;
import com.biblioteca.biblioteca_digital.model.Loan;
import com.biblioteca.biblioteca_digital.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDateTime;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUser(User user);
    List<Loan> findByStatus(LoanStatus status);
    long countByUserAndStatus(User user, LoanStatus status); // Para contar préstamos activos
    List<Loan> findByStatusAndDueDateBefore(LoanStatus status, LocalDateTime dateTime); // Para vencidos
}
