package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Database.Entity.Floor;
import com.example.spacebookingweb.Database.Entity.Reservation;
import com.example.spacebookingweb.Service.FloorService;
import com.example.spacebookingweb.Service.ReservationService;
import com.example.spacebookingweb.payload.response.MessageResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api/floor")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class FloorController {

    FloorService floorService;
    ReservationService reservationService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getFloorList() {
        List<Floor> floorList = floorService.getAll();

        if (!floorList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(floorList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Floor not found."));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getFloorById(@PathVariable("id") @Min(value = 1, message = "ID must be greater than or equal to 1") Long id) {
            Optional<Floor> optionalFloor = floorService.getFloorById(id);

            if(optionalFloor.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(optionalFloor);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Space with id: " + id + " not found."));
            }
    }

    @GetMapping(value = "/{id}/reservations/{date}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getFloorReservationsByFloorIdAndDate(@PathVariable("id") @Min(value = 1, message = "ID must be greater than or equal to 1") Long id,
                                                                  @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @JsonFormat(pattern = "yyyy/MM/dd") LocalDate date) {
        if(!floorService.checkFloorIsPresent(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Floor with id: " + id + " not found"));

        List<Reservation> reservationList = reservationService.getReservationsByFloorIdAndDate(id, date);

        if (!reservationList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(reservationList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Reservations not found"));
        }
    }
}