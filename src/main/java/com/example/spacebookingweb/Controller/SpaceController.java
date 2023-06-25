package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Database.Entity.Floor;
import com.example.spacebookingweb.Database.Entity.Space;
import com.example.spacebookingweb.Service.SpaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class SpaceController {
    SpaceService spaceService;

    @Operation(
            summary = "Get list of spaces",
            description = "Get list of spaces",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Space list"),
                    @ApiResponse(responseCode = "404", description = "Space not found")
            }
    )
    @GetMapping("/api/space")
    public ResponseEntity<List<Space>> getAll() {
        List<Space> spaceList= spaceService.getAll();

        if (spaceList == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(spaceList);
    }

    @Operation(
            summary = "Get space information by ID",
            description = "Get space information by ID. It returns ResponseEntity<Space>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Space information"),
                    @ApiResponse(responseCode = "404", description = "Space not found")
            }
    )
    @GetMapping("/api/space/{id}")
    public ResponseEntity<Space> getSpaceById(
            @PathVariable("id") Long id) {
        System.out.println(id);
        Space space = spaceService.getSpaceById(id);

        if (space == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(space);
    }

    @Operation(
            summary = "Get list of spaces with their information by the TYPE",
            description = "Get list of spaces with their information by the TYPE. It returns ResponseEntity<List<Space>>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of spaces with information"),
                    @ApiResponse(responseCode = "404", description = "Space not found")
            }
    )
    @GetMapping("/api/space/getByType/{type}")
    public ResponseEntity<List<Space>> getSpaceByType(
            @PathVariable("type") String type) {
        System.out.println(type);
        List<Space> spaceList = spaceService.getSpaceByType(type);

        if (spaceList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(spaceList);
    }

    @Operation(
            summary = "Get list of spaces which have / don't have height adjustment",
            description = "Get list of spaces which have / don't have height adjustment. ResponseEntity<List<Space>>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of spaces with height adjustment"),
                    @ApiResponse(responseCode = "404", description = "Space not found")
            }
    )
    @GetMapping("/api/space/getByHeightAdjustable/{bool}")
    public ResponseEntity<List<Space>> getSpaceByHeightAdjustable(
            @PathVariable("bool") Boolean bool) {
        System.out.println(bool);
        List<Space> spaceList = spaceService.getSpaceByHeightAdjustable(bool);

        if (spaceList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(spaceList);
    }

    @Operation(
            summary = "Get list of spaces which have / don't have height adjustment",
            description = "Get list of spaces which have / don't have height adjustment. ResponseEntity<List<Space>>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of spaces with height adjustment"),
                    @ApiResponse(responseCode = "404", description = "Space not found")
            }
    )
    @GetMapping("/api/space/getByFloorIdAndType/{id}/{type}/{date}")
    public ResponseEntity<List<Space>> getSpacesByFloorIdAndType(@PathVariable Long id, @PathVariable String type, @PathVariable LocalDate date) {
        List<Space> spaceList = spaceService.getSpacesByFloorIdAndType(id, type, date);

        if (spaceList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(spaceList);
    }
}
