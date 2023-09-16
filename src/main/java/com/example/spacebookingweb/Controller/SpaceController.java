package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Database.Entity.Space;
import com.example.spacebookingweb.Service.SpaceService;
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
