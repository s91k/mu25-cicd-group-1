package com.example.libraryAPI.repository;

import com.example.libraryAPI.model.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanRepository {

    Loan save(Loan loan);

    Optional<Loan> findById(int id);

    List<Loan> findAll();

    List<Loan> findByBookItemId(int bookItemId);
}
