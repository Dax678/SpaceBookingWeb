package com.example.spacebookingweb.Repository;

import com.example.spacebookingweb.Database.Entity.Reservation;
import com.example.spacebookingweb.Database.View.ReservationDetailsView;
import com.example.spacebookingweb.Database.View.UserReservationView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findReservationById(Long id);
    @Query(value = "SELECT view FROM ReservationDetailsView view " +
            "WHERE view.reservationDate >= :reservationStartDate " +
            "AND view.reservationDate <= :reservationEndDate")
    List<ReservationDetailsView> findAllReservationsWithInformationDetails(LocalDate reservationStartDate, LocalDate reservationEndDate);

    @Query(value = "SELECT view FROM UserReservationView view WHERE view.userId=:id")
    List<UserReservationView> findReservationsByUserId(Long id);

    @Query(value = "SELECT view FROM UserReservationView view " +
            "WHERE view.userId=:id " +
            "AND view.reservationDate >= current_date " +
            "AND view.reservationStatus = :bool")
    List<UserReservationView> findUserReservationListByStatus(Long id, Boolean bool);

    @Query(value = "SELECT reservation FROM Reservation reservation " +
            "WHERE reservation.spaceId=:SpaceId " +
            "AND reservation.reservationDate=:reservationDate " +
            "AND reservation.reservationStatus = true")
    Optional<Reservation> findReservationBySpaceIdAndReservationDate(Long SpaceId, LocalDate reservationDate);

    @Query(value = "SELECT reservation FROM Reservation reservation " +
            "INNER JOIN Space space " +
            "ON space.id=reservation.spaceId " +
            "WHERE space.floorId=:floorId " +
            "AND reservation.reservationDate=:date " +
            "AND reservation.reservationStatus=true")
    List<Reservation> findReservationByFloorIdAndReservationDate(Long floorId, LocalDate date);
}
