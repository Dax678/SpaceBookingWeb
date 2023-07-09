package com.example.spacebookingweb.Service;

import com.example.spacebookingweb.Database.Entity.Reservation;
import com.example.spacebookingweb.Database.View.UserReservationView;
import com.example.spacebookingweb.Repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {
    ReservationRepository reservationRepository;

    public Reservation getReservationById(Long id) {
        return reservationRepository.findReservationById(id);
    }

    public List<UserReservationView> getReservationByUserId(Long id) {
        return reservationRepository.findReservationByUserId(id);
    }

    public List<UserReservationView> getActiveReservationByUserId(Long id) {
        return reservationRepository.findActiveReservationByUserId(id);
    }

    public Reservation saveReservation(Long user_id, Long space_id, LocalDate reservation_date, Boolean reservation_status) {
        Reservation reservation = new Reservation();
        reservation.setUser_id(user_id);
        reservation.setSpace_id(space_id);
        reservation.setReservation_date(reservation_date);
        reservation.setReservation_status(reservation_status);
        return reservationRepository.save(reservation);
    }

    public void updateReservationStatus(Long user_id, Long reservation_id) {
        reservationRepository.setReservationStatus(user_id, reservation_id, Boolean.FALSE);
    }

}
