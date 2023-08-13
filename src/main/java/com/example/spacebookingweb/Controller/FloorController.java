package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Database.Entity.Floor;
import com.example.spacebookingweb.Database.Entity.Reservation;
import com.example.spacebookingweb.Service.FloorService;
import com.example.spacebookingweb.Service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/floor")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class FloorController {
    FloorService floorService;
    ReservationService reservationService;

    @Operation(
            summary = "Get floor list",
            description = "Get floor list",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Floor list"),
                    @ApiResponse(responseCode = "404", description = "Floor not found")
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Floor>> getFloorList() {
        List<Floor> floorList = floorService.getAll();

        if (!floorList.isEmpty()) {
            return ResponseEntity.ok(floorList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Get floor informations by FloorId",
            description = "Get floor information by FloorId",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Floor informations"),
                    @ApiResponse(responseCode = "404", description = "Floor not found")
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Floor> getFloorById(@PathVariable("id") Long id) {
            Optional<Floor> optionalFloor = floorService.getFloorById(id);

            if(optionalFloor.isPresent()) {
                return optionalFloor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
            } else {
                return ResponseEntity.notFound().build();
            }
    }

    @Operation(
            summary = "Get reservation list by FloorId and Date",
            description = "Get reservation list by FloorId and Date",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reservation list"),
                    @ApiResponse(responseCode = "404", description = "Reservations not found")
            }
    )
    @GetMapping(value = "/{id}/reservations/{date}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Reservation>> getFloorReservationsByFloorIdAndDate(@PathVariable("id") Long id, @PathVariable("date") LocalDate date) {
        List<Reservation> reservationList = reservationService.getReservationsByFloorIdAndDate(id, date);

        if (!reservationList.isEmpty()) {
            return ResponseEntity.ok(reservationList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}