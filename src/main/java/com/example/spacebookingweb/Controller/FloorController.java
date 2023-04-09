package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Database.Entity.Floor;
import com.example.spacebookingweb.Service.FloorService;
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

    @GetMapping("/api/getFloorsByDepartment/{type}")
    public ResponseEntity<List<Floor>> getFloorsByDepartment(
            @PathVariable("type") String type) {
        System.out.println(type);
        List<Floor> floorList = floorService.getFloorsByDepartment(type);

        if (floorList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(floorList);
    }
}
