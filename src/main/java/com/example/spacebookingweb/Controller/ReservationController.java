package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Database.Entity.Reservation;
import com.example.spacebookingweb.Service.ReservationService;
import com.example.spacebookingweb.payload.request.BookingRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class ReservationController {
    ReservationService reservationService;

    @Operation(
            summary = "Get reservation information by ID",
            description = "Get reservation information by ID. It returns ResponseEntity<Reservation>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reservation information"),
                    @ApiResponse(responseCode = "404", description = "Reservation not found")
            }
    )
    @GetMapping("/api/reservation/getById/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable("id") Long id) {
        Optional<Reservation> optionalReservation = reservationService.getReservationById(id);

        if (optionalReservation.isPresent()) {
            return ResponseEntity.ok(optionalReservation.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/reservation/add")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> addReservation(@Valid @RequestBody BookingRequest bookingRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //2023-05-04T10:30:00

        Reservation reservation = reservationService.saveReservation(bookingRequest.getUser_id(), bookingRequest.getSpace_id(), bookingRequest.getDate(), bookingRequest.getReservation_status());
        if (reservation != null) {
            return ResponseEntity.ok("Reservation saved successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving reservation.");
        }
    }

    @PutMapping(value = "/api/reservation/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Reservation> updateReservationStatus(@RequestBody String newStatus, @PathVariable(value = "id") Long reservationId) {
        Optional<Reservation> optionalReservation = reservationService.getReservationById(reservationId);
        if(optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setReservation_status(Boolean.valueOf(newStatus));
            reservationService.updateReservationStatus(reservation);
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}