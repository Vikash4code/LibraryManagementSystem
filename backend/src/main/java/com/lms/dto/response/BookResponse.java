package com.lms.dto.response;

import com.lms.entity.Book;

public record BookResponse(
        Long id,
        String title,
        String author,
        String isbn,
        String category,
        int totalCopies,
        int availableCopies,
        String coverImageUrl,
        String description
) {
    public static BookResponse from(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getCategory(),
                book.getTotalCopies(),
                book.getAvailableCopies(),
                book.getCoverImageUrl(),
                book.getDescription());
    }
}
