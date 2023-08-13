package com.example.spacebookingweb.Repository;

import com.example.spacebookingweb.Database.Entity.Reservation;
import com.example.spacebookingweb.Database.View.UserReservationView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
            "AND view.reservation_status = :bool")
    List<UserReservationView> findUserReservationListByStatus(Long id, Boolean bool);

    @Query(value = "SELECT reservation FROM Reservation reservation " +
            "WHERE reservation.space_id=:SpaceId " +
            "AND reservation.reservation_date=:reservationDate " +
            "AND reservation.reservation_status = true")
    Optional<Reservation> findReservationBySpaceIdAndReservationDate(Long SpaceId, LocalDate reservationDate);

    @Query(value = "SELECT reservation FROM Reservation reservation " +
            "INNER JOIN Space space " +
            "ON space.id=reservation.space_id " +
            "WHERE space.floor_id=:floorId " +
            "AND reservation.reservation_date=:date")
    List<Reservation> findReservationByFloorIdAndReservationDate(Long floorId, LocalDate date);
}
