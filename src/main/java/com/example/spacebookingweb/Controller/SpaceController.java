package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Configuration.PDFGeneratorConfig.PDFGeneratorSpaceDetails;
import com.example.spacebookingweb.Database.Entity.ESpace;
import com.example.spacebookingweb.Database.Entity.Space;
import com.example.spacebookingweb.Service.FloorService;
import com.example.spacebookingweb.Service.SpaceService;
import com.example.spacebookingweb.payload.response.MessageResponse;
import com.example.spacebookingweb.payload.response.ObjectMessageResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@RequestMapping("/api/space")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class SpaceController {

    private static final Logger LOGGER = LogManager.getLogger(SpaceController.class);
    private final SpaceService spaceService;
    private final FloorService floorService;

    /**
     * @return List of all spaces
     */
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getSpaceList() {
        List<Space> spaceList= spaceService.getSpaceList();

        // List should always have a data set
        // If there is no data found, return a 404
        if (!spaceList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(spaceList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Space not found."));
        }
    }

    /**
     * @param id the ID of the space
     * @return Space with the given ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getSpaceById(@PathVariable("id") @Min(value = 1, message = "ID must be greater than or equal to 1") Long id) {
       Optional<Space> optionalSpace = spaceService.getSpaceById(id);

       if(optionalSpace.isPresent()) {
           return ResponseEntity.status(HttpStatus.OK).body(optionalSpace);
       } else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Space with id: " + id + " not found."));
       }
    }

    /**
     * @param id the ID of the space
     * @param space Request body for updating the space
     * @return Updated space with the given ID
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateSpace(@PathVariable("id") @Min(value = 1, message = "ID must be greater than or equal to 1") long id,
                                         @Valid @RequestBody Space space) {
        if(!spaceService.checkSpaceIsPresent(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Space with id: " + id + " not found."));

        try {
            space.setId(id);
            spaceService.updateSpace(space);
            return ResponseEntity.status(HttpStatus.OK).body(new ObjectMessageResponse<Space>("Space has been successfully updated.", space));
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("A problem occurred during updating space."));
        }
    }

    /**
     * @param floorId the ID of the space
     * @return List of all spaces in the given floor
     */
    @GetMapping("/floor/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSpaceByFloorId(@PathVariable("id") @Min(value = 1, message = "ID must be greater than or equal to 1") Long floorId) {
        if(!floorService.checkFloorIsPresent(floorId)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Floor with id: " + floorId + " not found."));

        List<Space> spaceList = spaceService.getSpaceByFloorId(floorId);

        return ResponseEntity.status(HttpStatus.OK).body(spaceList);
    }

    /**
     * @param floorId the ID of the floor
     * @return ResponseEntity with the PDF of the space details
     */
    @GetMapping("/floor/{id}/filePDF")
    @PreAuthorize("hasRole('ADMIN')")
    //TODO: Przetestowac
    public ResponseEntity<?> generatePDF(@PathVariable("id") @Min(value = 1, message = "ID must be greater than or equal to 1") Long floorId) throws IOException {
        PDFGeneratorSpaceDetails generator = new PDFGeneratorSpaceDetails();

        if(!floorService.checkFloorIsPresent(floorId)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Floor with id: " + floorId + " not found"));

        generator.setSpaceList(spaceService.getSpaceByFloorId(floorId));

        byte[] pdfBytes = generator.generate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "space_report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")) + ".pdf");
        headers.setContentLength(pdfBytes.length);

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    /**
     * @param type the type of the space
     * @return List of all spaces with the given type
     */
    @GetMapping("/type/{type}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getSpaceListByType(@PathVariable("type") @Pattern(regexp = "Standard|Tech|Room|Motorbikes", message = "Type should be Standard or Tech or Room or Motorbikes") String type) {
        List<Space> spaceList = spaceService.getSpaceByType(ESpace.valueOf(type));

        return ResponseEntity.status(HttpStatus.OK).body(spaceList);
    }

    /**
     * @param bool the status of the space
     * @return List of all spaces with the given status
     */
    @GetMapping("/heightAdjustable/{bool}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getSpaceListByIsHeightAdjustable(@PathVariable("bool") @NotNull @Pattern(regexp = "true|false", message = "Status should be true or false") String bool) {
        List<Space> spaceList = spaceService.getSpaceByIsHeightAdjustable(Boolean.valueOf(bool));

        return ResponseEntity.status(HttpStatus.OK).body(spaceList);
    }

    /**
     * @param bool the status of the space
     * @return List of all spaces with the given status
     */
    @GetMapping("/isAvailable/{bool}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getSpacesByIsAvailable(@PathVariable("bool") @NotNull @Pattern(regexp = "true|false", message = "Status should be true or false") String bool) {
        List<Space> spaceList = spaceService.getSpacesByIsAvailable(Boolean.valueOf(bool));

        return ResponseEntity.status(HttpStatus.OK).body(spaceList);
    }

    /**
     * @param id the ID of the floor
     * @param type the type of the space
     * @param date the date of the space
     * @param status the status of the space
     * @return List of all spaces with the given parameters
     */
    @GetMapping("/{id}/{type}/{date}/{status}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getSpaceListByFloorIdAndType(@PathVariable(value = "id") @Min(value = 1, message = "ID must be greater than or equal to 1") Long id,
                                                          @PathVariable(value = "type") @Pattern(regexp = "Standard|Tech|Room|Motorbikes", message = "Type should be Standard or Tech or Room or Motorbikes") String type,
                                                          @PathVariable(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @JsonFormat(pattern = "yyyy/MM/dd") LocalDate date,
                                                          @PathVariable(value = "status") @NotNull @Pattern(regexp = "true|false", message = "Status should be true or false") String status) {

        if(!floorService.checkFloorIsPresent(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Floor with id: " + id + " not found"));

        List<Space> spaceList = spaceService.getSpacesByFloorIdAndTypeAndDateAndStatus(id, ESpace.valueOf(type), date, Boolean.parseBoolean(status));

        return ResponseEntity.status(HttpStatus.OK).body(spaceList);
    }
}
