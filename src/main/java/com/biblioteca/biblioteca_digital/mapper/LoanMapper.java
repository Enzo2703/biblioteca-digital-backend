package com.biblioteca.biblioteca_digital.mapper;

import com.biblioteca.biblioteca_digital.model.Loan;
import com.biblioteca.biblioteca_digital.dto.LoanDTO;

public class LoanMapper {

    // Convierte Loan a LoanDTO
    public static LoanDTO toDTO(Loan loan) {
        if (loan == null) return null;

        LoanDTO dto = new LoanDTO();
        dto.setId(loan.getId());
        dto.setUserId(loan.getUser() != null ? loan.getUser().getId() : null);
        dto.setBookId(loan.getBook() != null ? loan.getBook().getId() : null);
        dto.setLoanDate(loan.getLoanDate());
        dto.setDueDate(loan.getDueDate());
        dto.setReturnDate(loan.getReturnDate());
        dto.setStatus(loan.getStatus());
        dto.setFine(loan.getFine());

        return dto;
    }

    // Convierte LoanDTO a Loan
    public static Loan toEntity(LoanDTO dto) {
        if (dto == null) return null;

        Loan loan = new Loan();
        loan.setId(dto.getId());
        // Aquí deberías cargar user y book usando sus servicios, si es necesario:
        // loan.setUser(userService.findById(dto.getUserId()));
        // loan.setBook(bookService.findById(dto.getBookId()));
        loan.setLoanDate(dto.getLoanDate());
        loan.setDueDate(dto.getDueDate());
        loan.setReturnDate(dto.getReturnDate());
        loan.setStatus(dto.getStatus());
        loan.setFine(dto.getFine());

        return loan;
    }
}
