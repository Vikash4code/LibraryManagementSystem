package com.lms.service;

import com.lms.dto.request.IssueLoanRequest;
import com.lms.dto.response.LoanResponse;
import com.lms.entity.Book;
import com.lms.entity.Loan;
import com.lms.entity.User;
import com.lms.exception.ApiException;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.BookRepository;
import com.lms.repository.LoanRepository;
import com.lms.repository.ReservationRepository;
import com.lms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private LoanService loanService;

    private User user;
    private Book book;

    @BeforeEach
    void setUp() {
        // @Value fields are not injected in plain unit tests, set them manually
        ReflectionTestUtils.setField(loanService, "loanPeriodDays", 7);
        ReflectionTestUtils.setField(loanService, "finePerDay", 10);

        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");

        book = new Book();
        book.setId(10L);
        book.setTitle("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setTotalCopies(3);
        book.setAvailableCopies(2);
    }

    @Test
    void issueBook_decrementsCopiesAndSetsDueDate() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(10L)).thenReturn(Optional.of(book));
        when(loanRepository.existsByUserIdAndBookIdAndStatus(1L, 10L, Loan.Status.ISSUED)).thenReturn(false);
        when(reservationRepository.findByBookIdAndStatusOrderByReservedDateAsc(anyLong(), any()))
                .thenReturn(List.of());
        when(loanRepository.save(any(Loan.class))).thenAnswer(inv -> inv.getArgument(0));

        LoanResponse response = loanService.issueBook(new IssueLoanRequest(1L, 10L));

        assertThat(book.getAvailableCopies()).isEqualTo(1);
        assertThat(response.dueDate()).isEqualTo(LocalDate.now().plusDays(7));
        assertThat(response.status()).isEqualTo("ISSUED");
    }

    @Test
    void issueBook_failsWhenNoCopiesAvailable() {
        book.setAvailableCopies(0);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(10L)).thenReturn(Optional.of(book));

        assertThatThrownBy(() -> loanService.issueBook(new IssueLoanRequest(1L, 10L)))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("No copies");

        verify(loanRepository, never()).save(any());
    }

    @Test
    void issueBook_failsWhenUserAlreadyHasTheBook() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(10L)).thenReturn(Optional.of(book));
        when(loanRepository.existsByUserIdAndBookIdAndStatus(1L, 10L, Loan.Status.ISSUED)).thenReturn(true);

        assertThatThrownBy(() -> loanService.issueBook(new IssueLoanRequest(1L, 10L)))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("already has this book");
    }

    @Test
    void issueBook_failsWhenBookDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loanService.issueBook(new IssueLoanRequest(1L, 99L)))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void returnBook_onTime_hasNoFineAndIncrementsCopies() {
        Loan loan = activeLoan(LocalDate.now().plusDays(2)); // due in the future
        when(loanRepository.findById(5L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenAnswer(inv -> inv.getArgument(0));

        LoanResponse response = loanService.returnBook(5L);

        assertThat(response.fineAmount()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(response.status()).isEqualTo("RETURNED");
        assertThat(book.getAvailableCopies()).isEqualTo(3);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 10",
            "3, 30",
            "10, 100",
    })
    void returnBook_late_chargesTenRupeesPerDay(int daysLate, int expectedFine) {
        Loan loan = activeLoan(LocalDate.now().minusDays(daysLate));
        when(loanRepository.findById(5L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenAnswer(inv -> inv.getArgument(0));

        LoanResponse response = loanService.returnBook(5L);

        assertThat(response.fineAmount()).isEqualByComparingTo(BigDecimal.valueOf(expectedFine));
    }

    @Test
    void returnBook_failsWhenAlreadyReturned() {
        Loan loan = activeLoan(LocalDate.now());
        loan.setStatus(Loan.Status.RETURNED);
        when(loanRepository.findById(5L)).thenReturn(Optional.of(loan));

        assertThatThrownBy(() -> loanService.returnBook(5L))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("already returned");
    }

    private Loan activeLoan(LocalDate dueDate) {
        Loan loan = new Loan();
        loan.setId(5L);
        loan.setUser(user);
        loan.setBook(book);
        loan.setIssueDate(dueDate.minusDays(7));
        loan.setDueDate(dueDate);
        loan.setStatus(Loan.Status.ISSUED);
        return loan;
    }
}
