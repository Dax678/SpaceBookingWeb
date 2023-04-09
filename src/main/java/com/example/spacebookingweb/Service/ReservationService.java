package com.example.spacebookingweb.Service;

import com.example.spacebookingweb.Database.Entity.Reservation;
import com.example.spacebookingweb.Database.Entity.User;
import com.example.spacebookingweb.Repository.ReservationRepository;
import com.example.spacebookingweb.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {
    ReservationRepository reservationRepository;

    public Reservation getReservationById(Long id) {
        return reservationRepository.findReservationById(id);
    }

    public List<Reservation> getReservationByUserId(Long id) {
        return reservationRepository.findReservationByUserId(id);
    }
}
