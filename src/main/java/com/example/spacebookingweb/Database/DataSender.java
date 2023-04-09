package com.example.spacebookingweb.Database;

import com.example.spacebookingweb.Database.Entity.*;
import com.example.spacebookingweb.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@AllArgsConstructor
@Component
public class DataSender implements CommandLineRunner {
    private UserRepository userRepository;
    private UserDetailsRepository userDetailsRepository;
    private ReservationRepository reservationRepository;
    private FloorRepository floorRepository;
    private SpaceRepository spaceRepository;

    @Override
    public void run(String... args) throws Exception {
        //Mockowanie uzytkownika
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin123");
        user.setEmail("admin@example.com");
        userRepository.save(user);

        //Mocnkowanie danych detailicznych uzytkownika
        UserDetails userDetails = new UserDetails();
        userDetails.setId(1L);
        userDetails.setName("Jan");
        userDetails.setSurname("Kowalski");
        userDetails.setAddress("31-020, Warszawa, ul. warszawska 11/5A");
        userDetails.setPhoneNumber("00000000000");
        userDetailsRepository.save(userDetails);

        //Mockowanie danych pietra
        Floor floor = new Floor();
        floor.setId(1L);
        floor.setFloor_num("3");
        floor.setDepartment("IT");
        floorRepository.save(floor);

        floor.setId(2L);
        floor.setFloor_num("4");
        floor.setDepartment("IT");
        floorRepository.save(floor);

        floor.setId(3L);
        floor.setFloor_num("4");
        floor.setDepartment("HR");
        floorRepository.save(floor);

        floor.setId(4L);
        floor.setFloor_num("4");
        floor.setDepartment("IT");
        floorRepository.save(floor);

        floor.setId(5L);
        floor.setFloor_num("4");
        floor.setDepartment("Finance");
        floorRepository.save(floor);

        //Mockowanie danych miejsc
        Space space = new Space();
        space.setId(1L);
        space.setMonitorNumber(3);
        space.setType("Tech");
        space.setHeightAdjustable(Boolean.TRUE);
        spaceRepository.save(space);

        space.setId(2L);
        space.setMonitorNumber(3);
        space.setType("Tech");
        space.setHeightAdjustable(Boolean.FALSE);
        spaceRepository.save(space);

        space.setId(3L);
        space.setMonitorNumber(3);
        space.setType("Tech");
        space.setHeightAdjustable(Boolean.FALSE);
        spaceRepository.save(space);

        space.setId(4L);
        space.setMonitorNumber(3);
        space.setType("Tech");
        space.setHeightAdjustable(Boolean.TRUE);
        spaceRepository.save(space);

        space.setId(5L);
        space.setMonitorNumber(3);
        space.setType("Tech");
        space.setHeightAdjustable(Boolean.FALSE);
        spaceRepository.save(space);

        space.setId(6L);
        space.setMonitorNumber(2);
        space.setType("Standard");
        space.setHeightAdjustable(Boolean.FALSE);
        spaceRepository.save(space);

        space.setId(7L);
        space.setMonitorNumber(2);
        space.setType("Standard");
        space.setHeightAdjustable(Boolean.FALSE);
        spaceRepository.save(space);

        space.setId(8L);
        space.setMonitorNumber(2);
        space.setType("Standard");
        space.setHeightAdjustable(Boolean.FALSE);
        spaceRepository.save(space);

        space.setId(9L);
        space.setMonitorNumber(2);
        space.setType("Standard");
        space.setHeightAdjustable(Boolean.FALSE);
        spaceRepository.save(space);

        space.setId(10L);
        space.setMonitorNumber(2);
        space.setType("Standard");
        space.setHeightAdjustable(Boolean.FALSE);
        spaceRepository.save(space);

        //Mockowanie danych rezerwacji
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setUser_id(1L);
        reservation.setFloor_id(4L);
        reservation.setSpace_id(1L);
        reservation.setPayment_id(1L);
        reservation.setStart_date(new Date(2023, Calendar.FEBRUARY, 3, 8, 0));
        reservation.setEnd_date(new Date(2023, Calendar.FEBRUARY, 3, 17, 0));
        reservationRepository.save(reservation);

        reservation.setId(2L);
        reservation.setUser_id(1L);
        reservation.setFloor_id(4L);
        reservation.setSpace_id(1L);
        reservation.setPayment_id(2L);
        reservation.setStart_date(new Date(2023, Calendar.FEBRUARY, 4, 9, 30));
        reservation.setEnd_date(new Date(2023, Calendar.FEBRUARY, 4, 18, 30));
        reservationRepository.save(reservation);

        reservation.setId(3L);
        reservation.setUser_id(1L);
        reservation.setFloor_id(4L);
        reservation.setSpace_id(6L);
        reservation.setPayment_id(3L);
        reservation.setStart_date(new Date(2023, Calendar.MARCH, 21, 9, 0));
        reservation.setEnd_date(new Date(2023, Calendar.MARCH, 21, 11, 0));
        reservationRepository.save(reservation);
    }
}
