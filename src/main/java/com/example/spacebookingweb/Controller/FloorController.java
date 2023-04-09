package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Database.Entity.Floor;
import com.example.spacebookingweb.Service.FloorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class FloorController {
    FloorService floorService;

    @Operation(
            summary = "Get floor information by ID",
            description = "Get floor information by ID. It returns ResponseEntity<Floor>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Floor information"),
                    @ApiResponse(responseCode = "404", description = "Floor not found")
            }
    )
    @GetMapping("/api/getFloorById/{id}")
    public ResponseEntity<Floor> getFloorById(
            @PathVariable("id") Long id) {
        System.out.println(id);
        Floor floor = floorService.getFloorById(id);

        if (floor == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(floor);
    }

    @Operation(
            summary = "Get list of floors with their information by the TYPE",
            description = "Get list of floors with their information by the TYPE. It returns ResponseEntity<List<Floor>>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of floors with information"),
                    @ApiResponse(responseCode = "404", description = "Floor not found")
            }
    )
    @GetMapping("/api/getFloorsByName/{type}")
    public ResponseEntity<List<Floor>> getFloorsByName(
            @PathVariable("type") String type) {
        System.out.println(type);
        List<Floor> floorList = floorService.getFloorsByName(type);

        if (floorList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(floorList);
    }
}
