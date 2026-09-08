package com.example.libraryAPI.controller;

import com.example.libraryAPI.dto.CreateLoanRequest;
import com.example.libraryAPI.dto.LoanMapper;
import com.example.libraryAPI.dto.LoanResponse;
import com.example.libraryAPI.model.Loan;
import com.example.libraryAPI.service.LoanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(final LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public LoanResponse borrowBook(@RequestBody CreateLoanRequest request) {
        Loan loan = loanService.borrowBook(
                request.bookItemId(),
                request.borrowerId()
        );
        return LoanMapper.toResponse(loan);
    }

    @PostMapping("/{id}/return")
    public LoanResponse returnBook(@PathVariable int id) {
        return LoanMapper.toResponse(loanService.returnBook(id));
    }

    @GetMapping("/{id}")
    public LoanResponse getLoan(@PathVariable int id) {
        return LoanMapper.toResponse(loanService.getLoan(id));
    }

    @GetMapping
    public List<LoanResponse> getAllLoans() {
        return loanService.getAllLoans()
                          .stream()
                          .map(LoanMapper::toResponse)
                          .toList();
    }

    @GetMapping("/book-item/{bookItemId}")
    public List<LoanResponse> getLoansByBookItemId(@PathVariable int bookItemId) {
        return loanService.getLoansByBookItemId(bookItemId)
                          .stream()
                          .map(LoanMapper::toResponse)
                          .toList();
    }
}
