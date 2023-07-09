package com.example.spacebookingweb.Repository;

import com.example.spacebookingweb.Database.Entity.Reservation;
import com.example.spacebookingweb.Database.View.UserReservationView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation findReservationById(Long id);

    @Query(value = "UPDATE Reservation r SET r.reservation_status=:reservation_status " +
            "WHERE r.user_id = :user_id AND r.id = :reservation_id")
    void setReservationStatus(Long user_id, Long reservation_id, Boolean reservation_status);

    @Query(value = "SELECT view FROM UserReservationView view WHERE view.user_id=:id")
    List<UserReservationView> findReservationByUserId(Long id);

    @Query(value = "SELECT view FROM UserReservationView view " +
            "WHERE view.user_id=:id " +
            "AND view.reservation_date >= current_date " +
            "AND view.reservation_status = true")
    List<UserReservationView> findActiveReservationByUserId(Long id);
}
