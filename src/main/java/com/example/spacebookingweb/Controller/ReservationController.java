package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Configuration.PDFGeneratorConfig.PDFGeneratorReservationDetails;
import com.example.spacebookingweb.Database.Entity.Reservation;
import com.example.spacebookingweb.Database.View.ReservationDetailsView;
import com.example.spacebookingweb.Service.ReservationService;
import com.example.spacebookingweb.payload.response.MessageResponse;
import com.example.spacebookingweb.payload.response.ObjectMessageResponse;
import com.lowagie.text.DocumentException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api/reservation")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class ReservationController {

    private static final Logger LOGGER = LogManager.getLogger(ReservationController.class);

    ReservationService reservationService;

    /**
     * @param id ID of the reservation
     * @return Reservation with the given ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getReservationById(@PathVariable("id") @Min(value = 1, message = "ID must be greater than or equal to 1") Long id) {
        Optional<Reservation> optionalReservation = reservationService.getReservationById(id);

        if (optionalReservation.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(optionalReservation);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Reservation with id: " + id + " not found."));
        }
    }

    /**
     * @param reservation Request body of the reservation
     * @return Reservation with the given ID
     * @throws IllegalArgumentException
     */
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> addReservation(@Valid @RequestBody Reservation reservation) {
        if (reservationService.checkSpaceStatus(reservation.getSpaceId(), reservation.getReservationDate())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Reservation is already booked."));
        }

        try {
            reservation = reservationService.saveReservation(reservation);
            return new ResponseEntity<>(new ObjectMessageResponse<Reservation>("Reservation has been successfully added.", reservation), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("A problem occurred during saving a reservation."));
        }
    }

    /**
     * @param reservationId ID of the reservation
     * @param status Status of the reservation
     * @return Updated reservation with the given ID
     * @throws IllegalArgumentException
     */
    @PutMapping(value = "/{id}/status/{bool}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateReservationStatus(@PathVariable(value = "id") @Min(value = 1, message = "ID must be greater than or equal to 1") Long reservationId,
                                                     @PathVariable("bool") @NotNull @Pattern(regexp = "true|false", message = "Status should be true or false") String status) {
        if (!reservationService.checkReservationIsPresent(reservationId))
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Reservation with id: " + reservationId + " not found."));

        Optional<Reservation> reservationOptional = reservationService.getReservationById(reservationId);

        try {
            reservationOptional.get().setReservationStatus(Boolean.valueOf(status));
            return new ResponseEntity<>(new ObjectMessageResponse<Reservation>("Reservation status has been successfully updated.", reservationService.saveReservation(reservationOptional.get())), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("A problem occurred during updating reservation."));
        }
    }

    /**
     * @param reservationStartDate Start date of the reservation
     * @param reservationEndDate End date of the reservation
     * @return List of reservations for the given range of dates
     * @throws IllegalArgumentException
     */
    @GetMapping("/details")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllReservationDetailsByDateRange(@RequestParam("reservationStartDate") LocalDate reservationStartDate,
                                                                 @RequestParam("reservationEndDate") LocalDate reservationEndDate) {
        List<ReservationDetailsView> reservationList = reservationService.getAllReservationDetailsByDateRange(reservationStartDate, reservationEndDate);

        return ResponseEntity.status(HttpStatus.OK).body(reservationList);
    }

    /**
     * @param reservationStartDate Start date of the reservation
     * @param reservationEndDate End date of the reservation
     * @return ResponseEntity with the PDF of the reservation details
     */
    @GetMapping("/details/filePDF")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> generatePDF(@RequestParam("reservationStartDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reservationStartDate,
                                         @RequestParam("reservationEndDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reservationEndDate) {

        List<ReservationDetailsView> reservationList = reservationService.getAllReservationDetailsByDateRange(reservationStartDate, reservationEndDate);

        if (reservationList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Reservation not found."));
        }

        PDFGeneratorReservationDetails generator = new PDFGeneratorReservationDetails();
        generator.setReservationDetailsViewList(reservationList);
        byte[] pdfBytes;
        try {
            pdfBytes = generator.generate(reservationStartDate, reservationEndDate);
        } catch (IOException | DocumentException  e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("A problem occurred during generating PDF."));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "reservation_report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")) + ".pdf");
        headers.setContentLength(pdfBytes.length);

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}