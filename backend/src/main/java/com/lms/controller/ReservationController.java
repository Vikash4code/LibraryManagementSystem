package com.lms.controller;

import com.lms.dto.response.ReservationResponse;
import com.lms.service.ReservationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Tag(name = "Reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public record ReserveRequest(@NotNull(message = "Book id is required") Long bookId) {
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> reserve(Authentication authentication,
                                                       @Valid @RequestBody ReserveRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservationService.reserveBook(authentication.getName(), request.bookId()));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<ReservationResponse> cancel(Authentication authentication, @PathVariable Long id) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        return ResponseEntity.ok(reservationService.cancelReservation(authentication.getName(), id, isAdmin));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ReservationResponse>> myReservations(Authentication authentication) {
        return ResponseEntity.ok(reservationService.getMyReservations(authentication.getName()));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationResponse>> allReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }
}
