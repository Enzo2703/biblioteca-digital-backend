package com.biblioteca.biblioteca_digital.service;

import com.biblioteca.biblioteca_digital.exception.ResourceNotFoundException;
import com.biblioteca.biblioteca_digital.model.Loan;
import com.biblioteca.biblioteca_digital.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan getLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Préstamo no encontrado con id: " + id));
    }

    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    public Loan updateLoan(Long id, Loan loanDetails) {
        Loan loan = getLoanById(id);
        loan.setUser(loanDetails.getUser());
        loan.setBook(loanDetails.getBook());
        loan.setLoanDate(loanDetails.getLoanDate());
        loan.setDueDate(loanDetails.getDueDate());
        loan.setReturnDate(loanDetails.getReturnDate());
        loan.setStatus(loanDetails.getStatus());
        loan.setFine(loanDetails.getFine());
        return loanRepository.save(loan);
    }

    public void deleteLoan(Long id) {
        Loan loan = getLoanById(id);
        loanRepository.delete(loan);
    }
}
