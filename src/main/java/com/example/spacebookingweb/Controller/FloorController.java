package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Database.Entity.Floor;
import com.example.spacebookingweb.Service.FloorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class FloorController {
    FloorService floorService;

    @Operation(
            summary = "Get list of floors",
            description = "Get list of floors",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Floor list"),
                    @ApiResponse(responseCode = "404", description = "Floor not found")
            }
    )
    @GetMapping("/api/floor")
    public ResponseEntity<List<Floor>> getFloorList() {
        List<Floor> floorList = floorService.getAll();

        if (!floorList.isEmpty()) {
            return ResponseEntity.ok(floorList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Get floor information by ID",
            description = "Get floor information by ID. It returns ResponseEntity<Floor>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Floor information"),
                    @ApiResponse(responseCode = "404", description = "Floor not found")
            }
    )
    @GetMapping("/api/floor/{id}")
    public ResponseEntity<Floor> getFloorById(@PathVariable("id") Long id) {
        System.out.println(id);
        Optional<Floor> optionalFloor = floorService.getFloorById(id);

        if (optionalFloor.isPresent()) {
            return ResponseEntity.ok(optionalFloor.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Get list of floors with their information by the TYPE",
            description = "Get list of floors with their information by the TYPE. It returns ResponseEntity<List<Floor>>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of floors with information"),
                    @ApiResponse(responseCode = "404", description = "Floor not found")
            }
    )
    @GetMapping("/api/floor/getByName/{type}")
    public ResponseEntity<Floor> getFloorByName(
            @PathVariable("type") String type) {
        System.out.println(type);
        Optional<Floor> optionalFloor = floorService.getFloorByName(type);

        if (optionalFloor.isPresent()) {
            return ResponseEntity.ok(optionalFloor.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}