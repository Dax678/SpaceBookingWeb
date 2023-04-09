package com.example.spacebookingweb.Repository;

import com.example.spacebookingweb.Database.Entity.Reservation;
import com.example.spacebookingweb.Database.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation findReservationById(Long id);

//    @Query(value = "SELECT r.id, s.name, f.floor_num FROM User u " +
//            "INNER JOIN Reservation r " +
//            "ON u.id=r.id " +
//            "INNER JOIN Floor f " +
//            "ON f.id=r.floor_id " +
//            "INNER JOIN Space s " +
//            "ON s.id=r.space_id " +
//            "WHERE u.id=:id")
    List<Reservation> findReservationByUserId(Long id);
}
