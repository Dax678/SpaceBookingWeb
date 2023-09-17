package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Configuration.PDFGeneratorReservationDetails;
import com.example.spacebookingweb.Configuration.PDFGeneratorSpaceDetails;
import com.example.spacebookingweb.Database.Entity.Reservation;
import com.example.spacebookingweb.Database.Entity.Space;
import com.example.spacebookingweb.Service.FloorService;
import com.example.spacebookingweb.Service.SpaceService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
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
@RequestMapping("/api/space")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class SpaceController {
    SpaceService spaceService;

    @Operation(
            summary = "Get space list",
            description = "Get space list",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Space list"),
                    @ApiResponse(responseCode = "404", description = "Space not found")
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Space>> getSpaceList() {
        List<Space> spaceList= spaceService.getSpaceList();

        if (!spaceList.isEmpty()) {
            return ResponseEntity.ok(spaceList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Get space information by ID",
            description = "Get space information by ID. It returns ResponseEntity<Space>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Space information"),
                    @ApiResponse(responseCode = "404", description = "Space not found")
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Space> getSpaceById(@PathVariable("id") Long id) {
       Optional<Space> optionalSpace = spaceService.getSpaceById(id);

        return optionalSpace.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Space> updateSpaceStatus(@PathVariable("id") long id,
                                                   @RequestBody String newStatus) {
        Optional<Space> optionalSpace = spaceService.getSpaceById(id);

        if (optionalSpace.isPresent()) {
            Space space = optionalSpace.get();

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(newStatus);
                boolean status = jsonNode.get("newStatus").asBoolean();

                System.out.println("newStatus: " + status);
                space.setIsAvailable(status);
                System.out.println("Space info: " + space.getIsAvailable());
                spaceService.updateSpaceStatus(space);
                return ResponseEntity.ok(space);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/floor/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Space>> getSpaceByFloorId(@PathVariable("id") Long floorId) {
        List<Space> spaceList = spaceService.getSpaceByFloorId(floorId);

        if (!spaceList.isEmpty()) {
            return ResponseEntity.ok(spaceList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/floor/{id}/filePDF")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> generatePDF(@PathVariable("id") Long floorId) throws IOException {
        PDFGeneratorSpaceDetails generator = new PDFGeneratorSpaceDetails();
        generator.setSpaceList(spaceService.getSpaceByFloorId(floorId));

        byte[] pdfBytes = generator.generate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "space_report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")) + ".pdf");
        headers.setContentLength(pdfBytes.length);

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @Operation(
            summary = "Get list of spaces with their information by the TYPE",
            description = "Get list of spaces with their information by the TYPE. It returns ResponseEntity<List<Space>>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of spaces with information"),
                    @ApiResponse(responseCode = "404", description = "Space not found")
            }
    )
    @GetMapping("/type/{type}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Space>> getSpaceListByType(@PathVariable("type") String type) {
        System.out.println(type);
        List<Space> spaceList = spaceService.getSpaceByType(type);

        if (!spaceList.isEmpty()) {
            return ResponseEntity.ok(spaceList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Get space list by height adjustment",
            description = "Get space list by height adjustment",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of spaces with height adjustment"),
                    @ApiResponse(responseCode = "404", description = "Space not found")
            }
    )
    @GetMapping("/heightAdjustable/{bool}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Space>> getSpaceListByIsHeightAdjustable(@PathVariable("bool") Boolean bool) {
        List<Space> spaceList = spaceService.getSpaceByIsHeightAdjustable(bool);

        if (!spaceList.isEmpty()) {
            return ResponseEntity.ok(spaceList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Get",
            description = "Get",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of spaces with height adjustment"),
                    @ApiResponse(responseCode = "404", description = "Space not found")
            }
    )
    @GetMapping("/{id}/{type}/{date}/{status}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Space>> getSpaceListByFloorIdAndType(@PathVariable(value = "id") Long id,
                                                                    @PathVariable(value = "type") String type,
                                                                    @PathVariable(value = "date") LocalDate date,
                                                                    @PathVariable(value = "status") Boolean status) {
        List<Space> spaceList = spaceService.getSpacesByFloorIdAndType(id, type, date, status);

        if (!spaceList.isEmpty()) {
            return ResponseEntity.ok(spaceList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
