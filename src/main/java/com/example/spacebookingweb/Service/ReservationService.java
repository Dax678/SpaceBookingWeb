package com.example.spacebookingweb.Service;

import com.example.spacebookingweb.Database.Entity.Reservation;
import com.example.spacebookingweb.Database.Entity.User;
import com.example.spacebookingweb.Database.View.UserReservationView;
import com.example.spacebookingweb.Repository.ReservationRepository;
import com.example.spacebookingweb.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

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
}
