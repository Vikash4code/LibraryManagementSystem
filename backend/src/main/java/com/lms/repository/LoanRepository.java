package com.lms.repository;

import com.lms.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    boolean existsByUserIdAndBookIdAndStatus(Long userId, Long bookId, Loan.Status status);

    long countByUserIdAndStatus(Long userId, Loan.Status status);

    Page<Loan> findByUserIdOrderByIssueDateDesc(Long userId, Pageable pageable);

    List<Loan> findByUserId(Long userId);

    Page<Loan> findByStatus(Loan.Status status, Pageable pageable);

    long countByStatus(Loan.Status status);

    long countByStatusAndDueDateBefore(Loan.Status status, LocalDate date);

    // loans due soon, used by the daily email reminder job
    List<Loan> findByStatusAndDueDateBetween(Loan.Status status, LocalDate from, LocalDate to);
}
