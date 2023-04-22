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

    //TODO: Dodac usuwanie rezerwacji + zabezpiecznia
    void deleteReservationById(Long id);

    @Query(value = "SELECT view FROM UserReservationView view WHERE view.user_id=:id")
    List<UserReservationView> findReservationByUserId(Long id);

    @Query(value = "SELECT view FROM UserReservationView view WHERE view.user_id=:id AND view.start_date > current_date")
    List<UserReservationView> findActiveReservationByUserId(Long id);
}
