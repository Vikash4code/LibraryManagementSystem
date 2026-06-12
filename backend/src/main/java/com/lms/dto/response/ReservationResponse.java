package com.lms.dto.response;

import com.lms.entity.Reservation;

import java.time.LocalDate;

public record ReservationResponse(
        Long id,
        Long userId,
        String userName,
        Long bookId,
        String bookTitle,
        LocalDate reservedDate,
        String status
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getUser().getId(),
                reservation.getUser().getName(),
                reservation.getBook().getId(),
                reservation.getBook().getTitle(),
                reservation.getReservedDate(),
                reservation.getStatus().name());
    }
}
