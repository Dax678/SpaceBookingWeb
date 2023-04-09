//package com.example.spacebookingweb.Database;
//
//import com.example.spacebookingweb.Database.Entity.*;
//import com.example.spacebookingweb.Repository.*;
//import lombok.AllArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import javax.management.Query;
//import java.util.Calendar;
//import java.util.Date;
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
//        userRepository.save(user);
//
//        //Mocnkowanie danych detailicznych uzytkownika ----------------------------------------------------------
//        UserDetails userDetails = new UserDetails();
//        userDetails.setId(1L);
//        userDetails.setName("Jan");
//        userDetails.setSurname("Kowalski");
//        userDetails.setAddress("31-020, Warszawa, ul. warszawska 11/5A");
//        userDetails.setPhoneNumber("00000000000");
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
//        Space space = new Space();
//        space.setId(1L);
//        space.setFloor_id(3L);
//        space.setName("F3_001");
//        space.setMonitorNumber(3);
//        space.setType("Tech");
//        space.setHeightAdjustable(Boolean.TRUE);
//        spaceRepository.save(space);
//
//        space.setId(2L);
//        space.setFloor_id(3L);
//        space.setName("F3_002");
//        space.setMonitorNumber(3);
//        space.setType("Tech");
//        space.setHeightAdjustable(Boolean.FALSE);
//        spaceRepository.save(space);
//
//        space.setId(3L);
//        space.setFloor_id(3L);
//        space.setName("F3_003");
//        space.setMonitorNumber(3);
//        space.setType("Tech");
//        space.setHeightAdjustable(Boolean.FALSE);
//        spaceRepository.save(space);
//
//        space.setId(4L);
//        space.setFloor_id(3L);
//        space.setName("F3_004");
//        space.setMonitorNumber(3);
//        space.setType("Tech");
//        space.setHeightAdjustable(Boolean.TRUE);
//        spaceRepository.save(space);
//
//        space.setId(5L);
//        space.setFloor_id(3L);
//        space.setName("F3_005");
//        space.setMonitorNumber(3);
//        space.setType("Tech");
//        space.setHeightAdjustable(Boolean.FALSE);
//        spaceRepository.save(space);
//
//        space.setId(6L);
//        space.setFloor_id(3L);
//        space.setName("F3_006");
//        space.setMonitorNumber(2);
//        space.setType("Standard");
//        space.setHeightAdjustable(Boolean.FALSE);
//        spaceRepository.save(space);
//
//        space.setId(7L);
//        space.setFloor_id(3L);
//        space.setName("F3_007");
//        space.setMonitorNumber(2);
//        space.setType("Standard");
//        space.setHeightAdjustable(Boolean.FALSE);
//        spaceRepository.save(space);
//
//        space.setId(8L);
//        space.setFloor_id(3L);
//        space.setName("F3_008");
//        space.setMonitorNumber(2);
//        space.setType("Standard");
//        space.setHeightAdjustable(Boolean.FALSE);
//        spaceRepository.save(space);
//
//        space.setId(9L);
//        space.setFloor_id(3L);
//        space.setName("F3_009");
//        space.setMonitorNumber(2);
//        space.setType("Standard");
//        space.setHeightAdjustable(Boolean.FALSE);
//        spaceRepository.save(space);
//
//        space.setId(10L);
//        space.setFloor_id(3L);
//        space.setName("F3_010");
//        space.setMonitorNumber(2);
//        space.setType("Standard");
//        space.setHeightAdjustable(Boolean.FALSE);
//        spaceRepository.save(space);
//
//        space.setId(11L);
//        space.setFloor_id(5L);
//        space.setName("F-1_001");
//        //space.setMonitorNumber(2);
//        space.setType("Parking");
//        //space.setHeightAdjustable(Boolean.FALSE);
//        spaceRepository.save(space);
//
//        space.setId(12L);
//        space.setFloor_id(5L);
//        space.setName("F-1_002");
//        //space.setMonitorNumber(2);
//        space.setType("Parking");
//        //space.setHeightAdjustable(Boolean.FALSE);
//        spaceRepository.save(space);
//
//        space.setId(13L);
//        space.setFloor_id(5L);
//        space.setName("F-1_003");
//        //space.setMonitorNumber(2);
//        space.setType("Parking");
//        //space.setHeightAdjustable(Boolean.FALSE);
//        spaceRepository.save(space);
//
//        //Mockowanie danych rezerwacji ----------------------------------------------------------
//        Reservation reservation = new Reservation();
//        reservation.setId(1L);
//        reservation.setUser_id(1L);
//        reservation.setSpace_id(1L);
//        reservation.setPayment_id(1L);
//        reservation.setStart_date(new Date(2023, Calendar.FEBRUARY, 3, 8, 0));
//        reservation.setEnd_date(new Date(2023, Calendar.FEBRUARY, 3, 17, 0));
//        reservationRepository.save(reservation);
//
//        reservation.setId(2L);
//        reservation.setUser_id(1L);
//        reservation.setSpace_id(1L);
//        reservation.setPayment_id(2L);
//        reservation.setStart_date(new Date(2023, Calendar.FEBRUARY, 4, 9, 30));
//        reservation.setEnd_date(new Date(2023, Calendar.FEBRUARY, 4, 18, 30));
//        reservationRepository.save(reservation);
//
//        reservation.setId(3L);
//        reservation.setUser_id(1L);
//        reservation.setSpace_id(6L);
//        reservation.setPayment_id(3L);
//        reservation.setStart_date(new Date(2023, Calendar.MARCH, 21, 9, 0));
//        reservation.setEnd_date(new Date(2023, Calendar.MARCH, 21, 11, 0));
//        reservationRepository.save(reservation);
//    }
//}
