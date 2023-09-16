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
//        user.setPassword("admin");
//        user.setEmail("admin@example.com");
//        user.setRole(ERole.ROLE_ADMIN);
//        userRepository.save(user);
//
//        user.setUsername("JKowalski");
//        user.setPassword("password");
//        user.setEmail("jkowalski@example.com");
//        user.setRole(ERole.ROLE_USER);
//        userRepository.save(user);
//
//        user.setUsername("JNowak");
//        user.setPassword("password");
//        user.setEmail("jnowak@example.com");
//        user.setRole(ERole.ROLE_USER);
//        userRepository.save(user);
//
//        //Mocnkowanie danych detailicznych uzytkownika ----------------------------------------------------------
//        UserDetails userDetails = new UserDetails();
//        userDetails.setUserId(1L);
//        userDetails.setName("Tony");
//        userDetails.setSurname("Stank");
//        userDetails.setAddress("31-020, Warszawa, ul. warszawska 11/5A");
//        userDetails.setPhoneNumber("00000000000");
//        userDetailsRepository.save(userDetails);
//
//        userDetails.setUserId(2L);
//        userDetails.setName("Jan");
//        userDetails.setSurname("Kowalski");
//        userDetails.setAddress("31-020, Krakow, ul. krakowska 12/1A");
//        userDetails.setPhoneNumber("111111111");
//        userDetailsRepository.save(userDetails);
//
//        userDetails.setUserId(2L);
//        userDetails.setName("Jakub");
//        userDetails.setSurname("Nowak");
//        userDetails.setAddress("31-020, Krakow, ul. poznanska 18/5A");
//        userDetails.setPhoneNumber("22222222");
//        userDetailsRepository.save(userDetails);
//
//        //Mockowanie danych pietra ----------------------------------------------------------
//        Floor floor = new Floor();
//        floor.setId(1L);
//        floor.setFloorNum("1");
//        floor.setName("HR");
//        floorRepository.save(floor);
//
//        floor.setId(2L);
//        floor.setFloorNum("2");
//        floor.setName("Finance");
//        floorRepository.save(floor);
//
//        floor.setId(3L);
//        floor.setFloorNum("3");
//        floor.setName("IT");
//        floorRepository.save(floor);
//
//        floor.setId(4L);
//        floor.setFloorNum("-1");
//        floor.setName("Parking");
//        floorRepository.save(floor);
//
//        floor.setId(5L);
//        floor.setFloorNum("-2");
//        floor.setName("Parking");
//        floorRepository.save(floor);
//
//        //Mockowanie danych miejsc ----------------------------------------------------------
//
//
//        for (long i = 1; i <= 19; i++) {
//            Space space = new Space();
//            space.setFloorId(1L);
//            space.setName("F1_00" + i);
//            space.setMonitorNumber(3);
//            space.setType("Tech");
//            space.setHeightAdjustable(Boolean.TRUE);
//            space.setIsAvailable(true);
//            spaceRepository.save(space);
//        }
//
//        for (long i = 20; i <= 38; i++) {
//            Space space = new Space();
//            space.setFloorId(1L);
//            space.setName("F1_0" + i);
//            space.setMonitorNumber(2);
//            space.setType("Standard");
//            space.setHeightAdjustable(Boolean.FALSE);
//            space.setIsAvailable(true);
//            spaceRepository.save(space);
//        }
//
//        {
//            Space space = new Space();
//            space.setFloorId(1L);
//            space.setName("F1_101");
//            space.setMonitorNumber(0);
//            space.setType("Standard");
//            space.setHeightAdjustable(Boolean.FALSE);
//            space.setIsAvailable(true);
//            spaceRepository.save(space);
//        }
//
//        {
//            Space space = new Space();
//            space.setFloorId(1L);
//            space.setName("F1_102");
//            space.setMonitorNumber(0);
//            space.setType("Standard");
//            space.setHeightAdjustable(Boolean.FALSE);
//            space.setIsAvailable(true);
//            spaceRepository.save(space);
//        }
//
//
//
//        for (long i = 1; i <= 19; i++) {
//            Space space = new Space();
//            space.setFloorId(2L);
//            space.setName("F2_0" + i);
//            space.setMonitorNumber(3);
//            space.setType("Tech");
//            space.setHeightAdjustable(Boolean.TRUE);
//            space.setIsAvailable(true);
//            spaceRepository.save(space);
//        }
//
//        for (long i = 20; i <= 38; i++) {
//            Space space = new Space();
//            space.setFloorId(2L);
//            space.setName("F2_0" + i);
//            space.setMonitorNumber(2);
//            space.setType("Standard");
//            space.setHeightAdjustable(Boolean.FALSE);
//            space.setIsAvailable(true);
//            spaceRepository.save(space);
//        }
//
//        for (long i = 1; i <= 19; i++) {
//            Space space = new Space();
//            space.setFloorId(3L);
//            space.setName("F3_0" + i);
//            space.setMonitorNumber(3);
//            space.setType("Tech");
//            space.setHeightAdjustable(Boolean.TRUE);
//            space.setIsAvailable(true);
//            spaceRepository.save(space);
//        }
//
//        for (long i = 20; i <= 38; i++) {
//            Space space = new Space();
//            space.setFloorId(3L);
//            space.setName("F3_0" + i);
//            space.setMonitorNumber(2);
//            space.setType("Standard");
//            space.setHeightAdjustable(Boolean.FALSE);
//            space.setIsAvailable(true);
//            spaceRepository.save(space);
//        }
//
//        for (long i = 1; i <= 20; i++) {
//            Space space = new Space();
//            space.setFloorId(4L);
//            space.setName("F4_0" + i);
//            space.setType("Parking");
//            space.setIsAvailable(true);
//            spaceRepository.save(space);
//        }
//
//        for (long i = 1; i <= 20; i++) {
//            Space space = new Space();
//            space.setFloorId(5L);
//            space.setName("F5_0" + i);
//            space.setType("Parking");
//            space.setIsAvailable(true);
//            spaceRepository.save(space);
//        }
//
//        //Mockowanie danych rezerwacji ----------------------------------------------------------
//        Reservation reservation = new Reservation();
//        reservation.setId(1L);
//        reservation.setUserId(2L);
//        reservation.setSpaceId(47L);
//        reservation.setReservationDate(LocalDate.of(2024, Month.MAY, 24));
//        reservation.setReservationStatus(Boolean.TRUE);
//        reservationRepository.save(reservation);
//
//        reservation.setId(2L);
//        reservation.setUserId(2L);
//        reservation.setSpaceId(65L);
//        reservation.setReservationDate(LocalDate.of(2024, Month.MAY, 24));
//        reservation.setReservationStatus(Boolean.TRUE);
//        reservationRepository.save(reservation);
//
//        reservation.setId(3L);
//        reservation.setUserId(2L);
//        reservation.setSpaceId(47L);
//        reservation.setReservationDate(LocalDate.of(2024, Month.MAY, 25));
//        reservation.setReservationStatus(Boolean.TRUE);
//        reservationRepository.save(reservation);
//
//        reservation.setId(4L);
//        reservation.setUserId(2L);
//        reservation.setSpaceId(65L);
//        reservation.setReservationDate(LocalDate.of(2024, Month.MAY, 25));
//        reservation.setReservationStatus(Boolean.TRUE);
//        reservationRepository.save(reservation);
//
//        reservation.setId(5L);
//        reservation.setUserId(2L);
//        reservation.setSpaceId(48L);
//        reservation.setReservationDate(LocalDate.of(2024, Month.MAY, 26));
//        reservation.setReservationStatus(Boolean.TRUE);
//        reservationRepository.save(reservation);
//
//        reservation.setId(6L);
//        reservation.setUserId(2L);
//        reservation.setSpaceId(66L);
//        reservation.setReservationDate(LocalDate.of(2024, Month.MAY, 26));
//        reservation.setReservationStatus(Boolean.TRUE);
//        reservationRepository.save(reservation);
//    }
//}
