package com.lms.service;

import com.lms.dto.request.BookRequest;
import com.lms.dto.response.BookResponse;
import com.lms.entity.Book;
import com.lms.exception.ApiException;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private BookRequest sampleRequest(int totalCopies) {
        return new BookRequest("Clean Code", "Robert C. Martin", "9780132350884",
                "Programming", totalCopies, null, null);
    }

    @Test
    void addBook_setsAvailableCopiesToTotal() {
        when(bookRepository.existsByIsbn("9780132350884")).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        BookResponse response = bookService.addBook(sampleRequest(5));

        assertThat(response.availableCopies()).isEqualTo(5);
        assertThat(response.totalCopies()).isEqualTo(5);
    }

    @Test
    void addBook_failsForDuplicateIsbn() {
        when(bookRepository.existsByIsbn("9780132350884")).thenReturn(true);

        assertThatThrownBy(() -> bookService.addBook(sampleRequest(5)))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("ISBN");
    }

    @Test
    void updateBook_adjustsAvailableCopiesWhenTotalChanges() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setTotalCopies(5);
        book.setAvailableCopies(3); // 2 copies are out on loan

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        BookResponse response = bookService.updateBook(1L, sampleRequest(7)); // +2 copies

        assertThat(response.availableCopies()).isEqualTo(5);
    }

    @Test
    void updateBook_failsWhenReducingBelowIssuedCount() {
        Book book = new Book();
        book.setId(1L);
        book.setTotalCopies(5);
        book.setAvailableCopies(1); // 4 copies are out on loan

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        assertThatThrownBy(() -> bookService.updateBook(1L, sampleRequest(3)))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("currently issued");
    }

    @Test
    void deleteBook_failsWhenCopiesAreIssued() {
        Book book = new Book();
        book.setId(1L);
        book.setTotalCopies(5);
        book.setAvailableCopies(4);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        assertThatThrownBy(() -> bookService.deleteBook(1L))
                .isInstanceOf(ApiException.class);

        verify(bookRepository, never()).delete(any());
    }

    @Test
    void getBook_failsWhenNotFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getBook(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
