package com.example.spacebookingweb.Service;

import com.example.spacebookingweb.Database.Entity.Reservation;
import com.example.spacebookingweb.Database.View.ReservationDetailsView;
import com.example.spacebookingweb.Database.View.UserReservationView;
import com.example.spacebookingweb.Repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findReservationById(id);
    }

    public Boolean checkReservationIsPresent(Long id) {
        return  reservationRepository.existsById(id);
    }

    public List<UserReservationView> getReservationsByUserId(Long id) {
        return reservationRepository.findReservationsByUserId(id);
    }

    public List<UserReservationView> getUserReservationListByStatus(Long id, Boolean bool) {
        return reservationRepository.findUserReservationListByStatus(id, bool);
    }

    public Boolean checkSpaceStatus(Long id, LocalDate date) {
        Optional<Reservation> reservationOptional = reservationRepository.findReservationBySpaceIdAndReservationDate(id, date);

        return reservationOptional.isPresent();
    }

    @Transactional
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByFloorIdAndDate(Long floorId, LocalDate date) {
        return reservationRepository.findReservationByFloorIdAndReservationDate(floorId, date);
    }

    public List<ReservationDetailsView> getAllReservationDetailsByDateRange(LocalDate reservationStartDate, LocalDate reservationEndDate) {
        return reservationRepository.findAllReservationsWithInformationDetails(reservationStartDate, reservationEndDate);
    }
}
