package com.lms.service;

import com.lms.dto.request.IssueLoanRequest;
import com.lms.dto.response.LoanResponse;
import com.lms.entity.Book;
import com.lms.entity.Loan;
import com.lms.entity.Reservation;
import com.lms.entity.User;
import com.lms.exception.ApiException;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.BookRepository;
import com.lms.repository.LoanRepository;
import com.lms.repository.ReservationRepository;
import com.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    @Value("${lms.loan.period-days}")
    private int loanPeriodDays;

    @Value("${lms.loan.fine-per-day}")
    private int finePerDay;

    @Transactional
    public LoanResponse issueBook(IssueLoanRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + request.userId()));
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + request.bookId()));

        if (book.getAvailableCopies() <= 0) {
            throw new ApiException("No copies of this book are available");
        }
        if (loanRepository.existsByUserIdAndBookIdAndStatus(user.getId(), book.getId(), Loan.Status.ISSUED)) {
            throw new ApiException("This user already has this book issued");
        }

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setIssueDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(loanPeriodDays));

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        // if this user had reserved the book, the reservation is now fulfilled
        reservationRepository.findByBookIdAndStatusOrderByReservedDateAsc(book.getId(), Reservation.Status.PENDING)
                .stream()
                .filter(r -> r.getUser().getId().equals(user.getId()))
                .findFirst()
                .ifPresent(r -> {
                    r.setStatus(Reservation.Status.FULFILLED);
                    reservationRepository.save(r);
                });

        return LoanResponse.from(loanRepository.save(loan));
    }

    @Transactional
    public LoanResponse returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id " + loanId));

        if (loan.getStatus() == Loan.Status.RETURNED) {
            throw new ApiException("This loan is already returned");
        }

        LocalDate today = LocalDate.now();
        loan.setReturnDate(today);
        loan.setStatus(Loan.Status.RETURNED);
        loan.setFineAmount(calculateFine(loan.getDueDate(), today));

        Book book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        return LoanResponse.from(loanRepository.save(loan));
    }

    // fine = days late x rate, zero when returned on time
    BigDecimal calculateFine(LocalDate dueDate, LocalDate returnDate) {
        long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
        if (daysLate <= 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(daysLate * finePerDay);
    }

    // readOnly transactions keep the Hibernate session open while we map the
    // lazy user/book relations into DTOs (open-in-view is disabled)
    @Transactional(readOnly = true)
    public Page<LoanResponse> getAllLoans(Loan.Status status, Pageable pageable) {
        Page<Loan> loans = (status == null)
                ? loanRepository.findAll(pageable)
                : loanRepository.findByStatus(status, pageable);
        return loans.map(LoanResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<LoanResponse> getLoansForUser(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return loanRepository.findByUserIdOrderByIssueDateDesc(user.getId(), pageable)
                .map(LoanResponse::from);
    }
}
