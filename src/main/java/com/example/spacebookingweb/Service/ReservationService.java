package com.example.spacebookingweb.Service;

import com.example.spacebookingweb.Database.Entity.Reservation;
import com.example.spacebookingweb.Database.View.UserReservationView;
import com.example.spacebookingweb.Repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationService {
    ReservationRepository reservationRepository;

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findReservationById(id);
    }

    public List<UserReservationView> getReservationByUserId(Long id) {
        return reservationRepository.findReservationByUserId(id);
    }

    public List<UserReservationView> getUserReservationListByStatus(Long id, Boolean bool) {
        return reservationRepository.findUserReservationListByStatus(id, bool);
    }

    public Boolean checkSpaceStatus(Long id, LocalDate date) {
        Optional<Reservation> reservationOptional = reservationRepository.findReservationBySpaceIdAndReservationDate(id, date);

        return reservationOptional.isPresent();
    }

    @Transactional
    public Reservation saveReservation(Long user_id, Long space_id, LocalDate reservation_date, Boolean reservation_status) {
        if(checkSpaceStatus(space_id, reservation_date)) {
            return null;
        }

        Reservation reservation = new Reservation();
        reservation.setUser_id(user_id);
        reservation.setSpace_id(space_id);
        reservation.setReservation_date(reservation_date);
        reservation.setReservation_status(reservation_status);
        return reservationRepository.save(reservation);
    }

    @Transactional
    public void updateReservationStatus(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByFloorIdAndDate(Long floorId, LocalDate date) {
        return reservationRepository.findReservationByFloorIdAndReservationDate(floorId, date);
    }
}
