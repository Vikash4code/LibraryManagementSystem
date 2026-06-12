package com.lms.service;

import com.lms.dto.response.ReservationResponse;
import com.lms.entity.Book;
import com.lms.entity.Reservation;
import com.lms.entity.User;
import com.lms.exception.ApiException;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.BookRepository;
import com.lms.repository.ReservationRepository;
import com.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReservationResponse reserveBook(String userEmail, Long bookId) {
        User user = findUserByEmail(userEmail);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId));

        // reservations only make sense when the book is out of stock
        if (book.getAvailableCopies() > 0) {
            throw new ApiException("This book is available, you can borrow it directly");
        }
        if (reservationRepository.existsByUserIdAndBookIdAndStatus(user.getId(), bookId, Reservation.Status.PENDING)) {
            throw new ApiException("You have already reserved this book");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBook(book);
        return ReservationResponse.from(reservationRepository.save(reservation));
    }

    @Transactional
    public ReservationResponse cancelReservation(String userEmail, Long reservationId, boolean isAdmin) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id " + reservationId));

        User user = findUserByEmail(userEmail);
        if (!isAdmin && !reservation.getUser().getId().equals(user.getId())) {
            throw new ApiException("You can only cancel your own reservations");
        }
        if (reservation.getStatus() != Reservation.Status.PENDING) {
            throw new ApiException("Only pending reservations can be cancelled");
        }

        reservation.setStatus(Reservation.Status.CANCELLED);
        return ReservationResponse.from(reservationRepository.save(reservation));
    }

    // keeps the session open while mapping lazy user/book relations
    @Transactional(readOnly = true)
    public List<ReservationResponse> getMyReservations(String userEmail) {
        User user = findUserByEmail(userEmail);
        return reservationRepository.findByUserIdOrderByReservedDateDesc(user.getId()).stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
