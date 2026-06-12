package com.lms.dto.response;

import com.lms.entity.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanResponse(
        Long id,
        Long userId,
        String userName,
        Long bookId,
        String bookTitle,
        LocalDate issueDate,
        LocalDate dueDate,
        LocalDate returnDate,
        BigDecimal fineAmount,
        String status
) {
    public static LoanResponse from(Loan loan) {
        return new LoanResponse(
                loan.getId(),
                loan.getUser().getId(),
                loan.getUser().getName(),
                loan.getBook().getId(),
                loan.getBook().getTitle(),
                loan.getIssueDate(),
                loan.getDueDate(),
                loan.getReturnDate(),
                loan.getFineAmount(),
                loan.getStatus().name());
    }
}
