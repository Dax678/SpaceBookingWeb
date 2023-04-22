package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Database.Entity.Reservation;
import com.example.spacebookingweb.Service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@AllArgsConstructor
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
    @GetMapping("/api/getReservationById/{id}")
    public ResponseEntity<Reservation> getReservationById(
            @PathVariable("id") Long id) {
        System.out.println(id);
        Reservation reservation = reservationService.getReservationById(id);

        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(reservation);
    }

    @PostMapping("/api/addReservation")
    public ResponseEntity<String> addReservation(Long user_id, Long space_id, String start_date, String end_date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"); //2023-05-04T10:30:00

        Reservation reservation = reservationService.saveReservation(user_id, space_id, LocalDateTime.parse(start_date, formatter), LocalDateTime.parse(end_date, formatter));
        if (reservation != null) {
            return ResponseEntity.ok("Reservation saved successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving reservation.");
        }
    }

    @DeleteMapping("/api/deleteReservation/{id}")
    public void deleteReservationById(@PathVariable Long id) {
        reservationService.deleteReservationById(id);
    }
}
