package com.lms.service;

import com.lms.dto.request.BookRequest;
import com.lms.dto.response.BookResponse;
import com.lms.entity.Book;
import com.lms.exception.ApiException;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Page<BookResponse> getBooks(String search, String category, Pageable pageable) {
        // treat blank params as "no filter"
        String searchTerm = (search == null || search.isBlank()) ? null : search.trim();
        String categoryTerm = (category == null || category.isBlank()) ? null : category.trim();

        return bookRepository.search(searchTerm, categoryTerm, pageable)
                .map(BookResponse::from);
    }

    public BookResponse getBook(Long id) {
        return BookResponse.from(findBookOrThrow(id));
    }

    public List<String> getCategories() {
        return bookRepository.findDistinctCategories();
    }

    @Transactional
    public BookResponse addBook(BookRequest request) {
        if (request.isbn() != null && !request.isbn().isBlank()
                && bookRepository.existsByIsbn(request.isbn())) {
            throw new ApiException("A book with this ISBN already exists");
        }

        Book book = new Book();
        applyRequest(book, request);
        book.setAvailableCopies(request.totalCopies());
        return BookResponse.from(bookRepository.save(book));
    }

    @Transactional
    public BookResponse updateBook(Long id, BookRequest request) {
        Book book = findBookOrThrow(id);

        // adjust available copies by the same amount the total changed
        int diff = request.totalCopies() - book.getTotalCopies();
        int newAvailable = book.getAvailableCopies() + diff;
        if (newAvailable < 0) {
            throw new ApiException("Cannot reduce total copies below the number currently issued");
        }

        applyRequest(book, request);
        book.setAvailableCopies(newAvailable);
        return BookResponse.from(bookRepository.save(book));
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = findBookOrThrow(id);
        if (book.getAvailableCopies() < book.getTotalCopies()) {
            throw new ApiException("Cannot delete a book that has copies currently issued");
        }
        bookRepository.delete(book);
    }

    private Book findBookOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
    }

    private void applyRequest(Book book, BookRequest request) {
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setIsbn(request.isbn());
        book.setCategory(request.category());
        book.setTotalCopies(request.totalCopies());
        book.setCoverImageUrl(request.coverImageUrl());
        book.setDescription(request.description());
    }
}
