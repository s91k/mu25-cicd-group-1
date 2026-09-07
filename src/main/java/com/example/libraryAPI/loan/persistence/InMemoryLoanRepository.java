package com.example.libraryAPI.loan.persistence;

import com.example.libraryAPI.loan.domain.Loan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryLoanRepository implements LoanRepository {

    private final Map<Integer, Loan> loans = new ConcurrentHashMap<>();
    private int currentId = 0;

    @Override
    public Loan save(Loan loan) {
        if (loan.id() == 0) {
            loan = new Loan(
                    currentId++,
                    loan.bookItemId(),
                    loan.borrowerId(),
                    loan.borrowedDate(),
                    loan.returnedDate()
            );
        }

        loans.put(loan.id(), loan);
        return loan;
    }

    @Override
    public Optional<Loan> findById(int id) {
        return Optional.ofNullable(loans.get(id));
    }

    @Override
    public List<Loan> findAll() {
        return List.copyOf(loans.values());
    }

    @Override
    public List<Loan> findByBookItemId(int bookItemId) {
        return loans.values()
                    .stream()
                    .filter(loan -> loan.bookItemId() == bookItemId)
                    .toList();
    }
}
