package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Configuration.PDFGeneratorReservationDetails;
import com.example.spacebookingweb.Database.Entity.Reservation;
import com.example.spacebookingweb.Database.View.ReservationDetailsView;
import com.example.spacebookingweb.Service.ReservationService;
import com.example.spacebookingweb.payload.request.BookingRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reservation")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class ReservationController {
    ReservationService reservationService;

    @Operation(
            summary = "Get reservation information by reservationId",
            description = "Get reservation information by reservationId",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reservation information"),
                    @ApiResponse(responseCode = "404", description = "Reservation not found")
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Reservation> getReservationById(@PathVariable("id") Long id) {
        Optional<Reservation> optionalReservation = reservationService.getReservationById(id);

        return optionalReservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Add new reservation",
            description = "Add new reservation",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reservation saved"),
                    @ApiResponse(responseCode = "404", description = "Error saving reservation")
            }
    )
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> addReservation(@Valid @RequestBody BookingRequest bookingRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //2023-05-04T10:30:00

        Reservation reservation = reservationService.saveReservation(bookingRequest.getUserId(), bookingRequest.getSpaceId(), bookingRequest.getDate(), bookingRequest.getReservationStatus());
        if (reservation != null) {
            return ResponseEntity.ok("Reservation saved successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving reservation.");
        }
    }

    @Operation(
            summary = "Update reservation status",
            description = "Update reservation status",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reservation status updated"),
                    @ApiResponse(responseCode = "404", description = "Error updating reservation status")
            }
    )
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Reservation> updateReservationStatus(@PathVariable(value = "id") Long reservationId,
                                                               @RequestBody String newStatus) {
        Optional<Reservation> optionalReservation = reservationService.getReservationById(reservationId);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setReservationStatus(Boolean.valueOf(newStatus));
            reservationService.updateReservationStatus(reservation);
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/details")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationDetailsView>> getAllReservationsWithInformationDetails(@RequestParam("reservationStartDate") LocalDate reservationStartDate, @RequestParam("reservationEndDate") LocalDate reservationEndDate) {
        List<ReservationDetailsView> reservationList = reservationService.getAllReservationsWithInformationDetails(reservationStartDate, reservationEndDate);

        if (!reservationList.isEmpty()) {
            return ResponseEntity.ok(reservationList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/details/filePDF")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> generatePDF(@RequestParam("reservationStartDate") LocalDate reservationStartDate,
                                              @RequestParam("reservationEndDate") LocalDate reservationEndDate) throws IOException {

        PDFGeneratorReservationDetails generator = new PDFGeneratorReservationDetails();
        generator.setReservationDetailsViewList(reservationService.getAllReservationsWithInformationDetails(reservationStartDate, reservationEndDate));
        byte[] pdfBytes = generator.generate(reservationStartDate, reservationEndDate);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "reservation_report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")) + ".pdf");
        headers.setContentLength(pdfBytes.length);

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}