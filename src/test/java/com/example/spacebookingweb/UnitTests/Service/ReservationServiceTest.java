package com.example.spacebookingweb.UnitTests.Service;

import com.example.spacebookingweb.Database.Entity.Reservation;
import com.example.spacebookingweb.Database.View.ReservationDetailsView;
import com.example.spacebookingweb.Database.View.UserReservationView;
import com.example.spacebookingweb.Repository.ReservationRepository;
import com.example.spacebookingweb.Service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetReservationById() {
        Long reservationId = 1L;

        Reservation expectedReservation = new Reservation();
        expectedReservation.setId(reservationId);

        Mockito.when(reservationRepository.findReservationById(reservationId)).thenReturn(Optional.of(expectedReservation));

        Optional<Reservation> reservation = reservationService.getReservationById(reservationId);

        assertTrue(reservation.isPresent());
        assertEquals(expectedReservation.getId(), reservation.get().getId());
    }

    @Test
    void testCheckReservationIsPresent() {
        Long reservationId = 1L;
        Mockito.when(reservationRepository.existsById(reservationId)).thenReturn(true);

        Boolean result = reservationService.checkReservationIsPresent(reservationId);

        assertTrue(result);
    }

    //TODO: Poprawić, false true
    @Test
    void testGetReservationsByUserId() {
        Long userId = 1L;
        List<UserReservationView> userReservations = new ArrayList<>();
        Mockito.when(reservationRepository.findReservationsByUserId(userId)).thenReturn(userReservations);

        List<UserReservationView> result = reservationService.getReservationsByUserId(userId);

        assertNotNull(result);
        assertEquals(userReservations, result);
    }

    //TODO: Poprawić, false true
    @Test
    void testGetUserReservationListByStatus() {
        Long userId = 1L;
        Boolean bool = true;

        List<UserReservationView> userReservations = new ArrayList<>();
        Mockito.when(reservationRepository.findUserReservationListByStatus(userId, bool)).thenReturn(userReservations);

        List<UserReservationView> result = reservationService.getUserReservationListByStatus(userId, false);

        assertNotNull(result);
        assertEquals(userReservations, result);
    }

    @Test
    void testCheckSpaceStatus() {
        Long spaceId = 1L;
        LocalDate date = LocalDate.now();
        Optional<Reservation> reservationOptional = Optional.of(new Reservation());
        Mockito.when(reservationRepository.findReservationBySpaceIdAndReservationDate(spaceId, date)).thenReturn(reservationOptional);

        Boolean result = reservationService.checkSpaceStatus(spaceId, date);

        assertTrue(result);
    }

    @Test
    void testSaveReservation() {
        Reservation reservation = new Reservation();
        Mockito.when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation result = reservationService.saveReservation(reservation);

        assertNotNull(result);
        assertEquals(reservation, result);
    }

    @Test
    void testGetReservationsByFloorIdAndDate() {
        Long floorId = 1L;
        LocalDate date = LocalDate.now();
        List<Reservation> reservations = new ArrayList<>();
        Mockito.when(reservationRepository.findReservationByFloorIdAndReservationDate(floorId, date)).thenReturn(reservations);

        List<Reservation> result = reservationService.getReservationsByFloorIdAndDate(floorId, date);

        assertNotNull(result);
        assertEquals(reservations, result);
    }

    @Test
    void testGetAllReservationDetailsByDateRange() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        List<ReservationDetailsView> reservationDetails = new ArrayList<>();
        Mockito.when(reservationRepository.findAllReservationsWithInformationDetails(startDate, endDate)).thenReturn(reservationDetails);

        List<ReservationDetailsView> result = reservationService.getAllReservationDetailsByDateRange(startDate, endDate);

        assertNotNull(result);
        assertEquals(reservationDetails, result);
    }
}