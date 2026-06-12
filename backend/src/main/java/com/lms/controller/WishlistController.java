package com.lms.controller;

import com.lms.dto.response.BookResponse;
import com.lms.service.WishlistService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
@Tag(name = "Wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public record WishlistRequest(@NotNull(message = "Book id is required") Long bookId) {
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getWishlist(Authentication authentication) {
        return ResponseEntity.ok(wishlistService.getWishlist(authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<Void> addToWishlist(Authentication authentication,
                                              @Valid @RequestBody WishlistRequest request) {
        wishlistService.addToWishlist(authentication.getName(), request.bookId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> removeFromWishlist(Authentication authentication,
                                                   @PathVariable Long bookId) {
        wishlistService.removeFromWishlist(authentication.getName(), bookId);
        return ResponseEntity.noContent().build();
    }
}
