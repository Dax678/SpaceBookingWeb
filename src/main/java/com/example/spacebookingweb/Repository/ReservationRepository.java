package com.example.spacebookingweb.Repository;

import com.example.spacebookingweb.Database.Entity.Reservation;
import com.example.spacebookingweb.Database.View.UserReservationView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findReservationById(Long id);

    @Query(value = "SELECT view FROM UserReservationView view WHERE view.user_id=:id")
    List<UserReservationView> findReservationByUserId(Long id);

    @Query(value = "SELECT view FROM UserReservationView view " +
            "WHERE view.user_id=:id " +
            "AND view.reservation_date >= current_date " +
            "AND view.reservation_status = true")
    List<UserReservationView> findActiveReservationByUserId(Long id);
}
