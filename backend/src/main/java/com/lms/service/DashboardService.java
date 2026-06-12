package com.lms.service;

import com.lms.entity.Loan;
import com.lms.repository.BookRepository;
import com.lms.repository.LoanRepository;
import com.lms.repository.ReservationRepository;
import com.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final ReservationRepository reservationRepository;

    public Map<String, Object> getAdminStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalBooks", bookRepository.count());
        stats.put("totalUsers", userRepository.count());
        stats.put("activeLoans", loanRepository.countByStatus(Loan.Status.ISSUED));
        stats.put("overdueLoans", loanRepository.countByStatusAndDueDateBefore(Loan.Status.ISSUED, LocalDate.now()));
        stats.put("booksPerCategory", booksPerCategory());
        stats.put("loansPerMonth", loansPerMonth());
        return stats;
    }

    public Map<String, Object> getUserStats(Long userId) {
        List<Loan> loans = loanRepository.findByUserId(userId);

        long active = loans.stream().filter(l -> l.getStatus() == Loan.Status.ISSUED).count();
        long overdue = loans.stream()
                .filter(l -> l.getStatus() == Loan.Status.ISSUED && l.getDueDate().isBefore(LocalDate.now()))
                .count();
        BigDecimal totalFines = loans.stream()
                .map(Loan::getFineAmount)
                .filter(f -> f != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> stats = new HashMap<>();
        stats.put("activeLoans", active);
        stats.put("overdueLoans", overdue);
        stats.put("totalBorrowed", loans.size());
        stats.put("totalFines", totalFines);
        return stats;
    }

    // [{ category: "Fiction", count: 12 }, ...] for the pie chart
    private List<Map<String, Object>> booksPerCategory() {
        Map<String, Long> grouped = new HashMap<>();
        bookRepository.findAll().forEach(book -> {
            String category = book.getCategory() == null ? "Other" : book.getCategory();
            grouped.merge(category, 1L, Long::sum);
        });
        return grouped.entrySet().stream()
                .map(e -> Map.<String, Object>of("category", e.getKey(), "count", e.getValue()))
                .toList();
    }

    // last 6 months of loan counts for the bar chart
    private List<Map<String, Object>> loansPerMonth() {
        List<Loan> allLoans = loanRepository.findAll();
        LocalDate now = LocalDate.now();

        return java.util.stream.IntStream.rangeClosed(0, 5)
                .mapToObj(i -> now.minusMonths(5 - i))
                .map(month -> {
                    long count = allLoans.stream()
                            .filter(l -> l.getIssueDate().getYear() == month.getYear()
                                    && l.getIssueDate().getMonth() == month.getMonth())
                            .count();
                    return Map.<String, Object>of(
                            "month", month.getMonth().name().substring(0, 3),
                            "count", count);
                })
                .toList();
    }
}
