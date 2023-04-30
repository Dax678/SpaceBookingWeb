//package com.example.spacebookingweb.Database;
//
//import com.example.spacebookingweb.Database.Entity.*;
//import com.example.spacebookingweb.Repository.*;
//import lombok.AllArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.Month;
//
//// Floor
//// FloorId: 1-3 -> Working area, 4-5 -> Parking Space
//
//@AllArgsConstructor
//@Component
//public class DataSender implements CommandLineRunner {
//    private UserRepository userRepository;
//    private UserDetailsRepository userDetailsRepository;
//    private ReservationRepository reservationRepository;
//    private FloorRepository floorRepository;
//    private SpaceRepository spaceRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        //Mockowanie uzytkownika ----------------------------------------------------------
//        User user = new User();
//        user.setUsername("admin");
//        user.setPassword("admin123");
//        user.setEmail("admin@example.com");
//        user.setRole("ADMIN");
//        userRepository.save(user);
//
//        user.setUsername("JKowalski");
//        user.setPassword("password");
//        user.setEmail("jkowalski@example.com");
//        user.setRole("USER");
//        userRepository.save(user);
//
//        //Mocnkowanie danych detailicznych uzytkownika ----------------------------------------------------------
//        UserDetails userDetails = new UserDetails();
//        userDetails.setId(1L);
//        userDetails.setName("Tony");
//        userDetails.setSurname("Stank");
//        userDetails.setAddress("31-020, Warszawa, ul. warszawska 11/5A");
//        userDetails.setPhoneNumber("00000000000");
//        userDetailsRepository.save(userDetails);
//
//        userDetails.setId(2L);
//        userDetails.setName("Jan");
//        userDetails.setSurname("Kowalski");
//        userDetails.setAddress("31-020, Krakow, ul. krakowska 12/1A");
//        userDetails.setPhoneNumber("111111111");
//        userDetailsRepository.save(userDetails);
//
//        //Mockowanie danych pietra ----------------------------------------------------------
//        Floor floor = new Floor();
//        floor.setId(1L);
//        floor.setFloor_num("1");
//        floor.setName("HR");
//        floorRepository.save(floor);
//
//        floor.setId(2L);
//        floor.setFloor_num("2");
//        floor.setName("Finance");
//        floorRepository.save(floor);
//
//        floor.setId(3L);
//        floor.setFloor_num("3");
//        floor.setName("IT");
//        floorRepository.save(floor);
//
//        floor.setId(4L);
//        floor.setFloor_num("-1");
//        floor.setName("Parking");
//        floorRepository.save(floor);
//
//        floor.setId(5L);
//        floor.setFloor_num("-2");
//        floor.setName("Parking");
//        floorRepository.save(floor);
//
//        //Mockowanie danych miejsc ----------------------------------------------------------
//
//        for (long i = 1; i <= 10; i++) {
//            Space space = new Space();
//            space.setFloor_id(1L);
//            space.setName("F1_00" + i);
//            space.setMonitorNumber(3);
//            space.setType("Tech");
//            space.setHeightAdjustable(Boolean.TRUE);
//            spaceRepository.save(space);
//        }
//
//        for (long i = 11; i <= 20; i++) {
//            Space space = new Space();
//            space.setFloor_id(1L);
//            space.setName("F1_0" + i);
//            space.setMonitorNumber(2);
//            space.setType("Standard");
//            space.setHeightAdjustable(Boolean.FALSE);
//            spaceRepository.save(space);
//        }
//
//        for (long i = 1; i <= 10; i++) {
//            Space space = new Space();
//            space.setFloor_id(2L);
//            space.setName("F2_0" + i);
//            space.setMonitorNumber(3);
//            space.setType("Tech");
//            space.setHeightAdjustable(Boolean.TRUE);
//            spaceRepository.save(space);
//        }
//
//        for (long i = 11; i <= 20; i++) {
//            Space space = new Space();
//            space.setFloor_id(2L);
//            space.setName("F2_0" + i);
//            space.setMonitorNumber(2);
//            space.setType("Standard");
//            space.setHeightAdjustable(Boolean.FALSE);
//            spaceRepository.save(space);
//        }
//
//        for (long i = 1; i <= 10; i++) {
//            Space space = new Space();
//            space.setFloor_id(3L);
//            space.setName("F3_0" + i);
//            space.setMonitorNumber(3);
//            space.setType("Tech");
//            space.setHeightAdjustable(Boolean.TRUE);
//            spaceRepository.save(space);
//        }
//
//        for (long i = 11; i <= 20; i++) {
//            Space space = new Space();
//            space.setFloor_id(3L);
//            space.setName("F3_0" + i);
//            space.setMonitorNumber(2);
//            space.setType("Standard");
//            space.setHeightAdjustable(Boolean.FALSE);
//            spaceRepository.save(space);
//        }
//
//        for (long i = 1; i <= 20; i++) {
//            Space space = new Space();
//            space.setFloor_id(4L);
//            space.setName("F4_0" + i);
//            space.setType("Parking");
//            spaceRepository.save(space);
//        }
//
//        for (long i = 1; i <= 20; i++) {
//            Space space = new Space();
//            space.setFloor_id(5L);
//            space.setName("F5_0" + i);
//            space.setType("Parking");
//            spaceRepository.save(space);
//        }
//
//        //Mockowanie danych rezerwacji ----------------------------------------------------------
//        Reservation reservation = new Reservation();
//        reservation.setId(1L);
//        reservation.setUser_id(2L);
//        reservation.setSpace_id(47L);
//        reservation.setReservation_date(LocalDate.of(2023, Month.MAY, 24));
//        reservationRepository.save(reservation);
//
//        reservation.setId(2L);
//        reservation.setUser_id(2L);
//        reservation.setSpace_id(65L);
//        reservation.setReservation_date(LocalDate.of(2023, Month.MAY, 24));
//        reservationRepository.save(reservation);
//
//        reservation.setId(3L);
//        reservation.setUser_id(2L);
//        reservation.setSpace_id(47L);
//        reservation.setReservation_date(LocalDate.of(2023, Month.MAY, 25));
//        reservationRepository.save(reservation);
//
//        reservation.setId(4L);
//        reservation.setUser_id(2L);
//        reservation.setSpace_id(65L);
//        reservation.setReservation_date(LocalDate.of(2023, Month.MAY, 25));
//        reservationRepository.save(reservation);
//
//        reservation.setId(5L);
//        reservation.setUser_id(2L);
//        reservation.setSpace_id(48L);
//        reservation.setReservation_date(LocalDate.of(2023, Month.MAY, 26));
//        reservationRepository.save(reservation);
//
//        reservation.setId(6L);
//        reservation.setUser_id(2L);
//        reservation.setSpace_id(66L);
//        reservation.setReservation_date(LocalDate.of(2023, Month.MAY, 26));
//        reservationRepository.save(reservation);
//    }
//}
