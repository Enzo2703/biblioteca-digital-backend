package com.biblioteca.biblioteca_digital.controller;

import com.biblioteca.biblioteca_digital.dto.LoanDTO;
import com.biblioteca.biblioteca_digital.mapper.LoanMapper;
import com.biblioteca.biblioteca_digital.model.Loan;
import com.biblioteca.biblioteca_digital.service.LoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public List<LoanDTO> getAll() {
        return loanService.getAllLoans()
                          .stream()
                          .map(LoanMapper::toDTO)
                          .toList();
    }

    @GetMapping("/{id}")
    public LoanDTO getById(@PathVariable Long id) {
        return LoanMapper.toDTO(loanService.getLoanById(id));
    }

    @PostMapping
    public LoanDTO create(@RequestBody LoanDTO loanDTO) {
        Loan loan = LoanMapper.toEntity(loanDTO);
        return LoanMapper.toDTO(loanService.createLoan(loan));
    }

    @PutMapping("/{id}")
    public LoanDTO update(@PathVariable Long id, @RequestBody LoanDTO loanDTO) {
        Loan loan = LoanMapper.toEntity(loanDTO);
        return LoanMapper.toDTO(loanService.updateLoan(id, loan));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return "Préstamo eliminado exitosamente";
    }
}

