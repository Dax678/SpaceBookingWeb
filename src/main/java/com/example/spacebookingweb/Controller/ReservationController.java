package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Database.Entity.Reservation;
import com.example.spacebookingweb.Database.Entity.User;
import com.example.spacebookingweb.Service.ReservationService;
import com.example.spacebookingweb.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
