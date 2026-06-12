package com.lms.repository;

import com.lms.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByUserIdAndBookIdAndStatus(Long userId, Long bookId, Reservation.Status status);

    List<Reservation> findByUserIdOrderByReservedDateDesc(Long userId);

    List<Reservation> findByStatusOrderByReservedDateAsc(Reservation.Status status);

    List<Reservation> findByBookIdAndStatusOrderByReservedDateAsc(Long bookId, Reservation.Status status);
}
