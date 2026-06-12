package com.lms.service;

import com.lms.dto.response.BookResponse;
import com.lms.entity.Book;
import com.lms.entity.User;
import com.lms.entity.Wishlist;
import com.lms.exception.ApiException;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.BookRepository;
import com.lms.repository.UserRepository;
import com.lms.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    // keeps the session open while mapping the lazy book relation
    @Transactional(readOnly = true)
    public List<BookResponse> getWishlist(String userEmail) {
        User user = findUserByEmail(userEmail);
        return wishlistRepository.findByUserIdOrderByAddedAtDesc(user.getId()).stream()
                .map(item -> BookResponse.from(item.getBook()))
                .toList();
    }

    @Transactional
    public void addToWishlist(String userEmail, Long bookId) {
        User user = findUserByEmail(userEmail);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId));

        if (wishlistRepository.existsByUserIdAndBookId(user.getId(), bookId)) {
            throw new ApiException("This book is already in your wishlist");
        }

        Wishlist item = new Wishlist();
        item.setUser(user);
        item.setBook(book);
        wishlistRepository.save(item);
    }

    @Transactional
    public void removeFromWishlist(String userEmail, Long bookId) {
        User user = findUserByEmail(userEmail);
        Wishlist item = wishlistRepository.findByUserIdAndBookId(user.getId(), bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book is not in your wishlist"));
        wishlistRepository.delete(item);
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
