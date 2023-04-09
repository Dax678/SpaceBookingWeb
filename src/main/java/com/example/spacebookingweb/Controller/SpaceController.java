package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Database.Entity.Space;
import com.example.spacebookingweb.Service.SpaceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class SpaceController {
    SpaceService spaceService;

    @GetMapping("/api/getSpaceById/{id}")
    public ResponseEntity<Space> getSpaceById(
            @PathVariable("id") Long id) {
        System.out.println(id);
        Space space = spaceService.getSpaceById(id);

        if (space == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(space);
    }

    @GetMapping("/api/getSpaceByType/{type}")
    public ResponseEntity<List<Space>> getSpaceByType(
            @PathVariable("type") String type) {
        System.out.println(type);
        List<Space> spaceList = spaceService.getSpaceByType(type);

        if (spaceList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(spaceList);
    }

    @GetMapping("/api/getSpaceByHeightAdjustable/{bool}")
    public ResponseEntity<List<Space>> getSpaceByHeightAdjustable(
            @PathVariable("bool") Boolean bool) {
        System.out.println(bool);
        List<Space> spaceList = spaceService.getSpaceByHeightAdjustable(bool);

        if (spaceList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(spaceList);
    }
}
