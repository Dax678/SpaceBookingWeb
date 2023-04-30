package com.example.spacebookingweb.Service;

import com.example.spacebookingweb.Database.Entity.Space;
import com.example.spacebookingweb.Repository.SpaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class SpaceService {
    SpaceRepository spaceRepository;

    public List<Space> getAll() {
        return spaceRepository.findAll();
    }

    public Space getSpaceById(Long id) {
        return spaceRepository.findSpaceById(id);
    }

    public List<Space> getSpaceByType(String type) {
        return spaceRepository.findSpaceByType(type);
    }

    public List<Space> getSpaceByHeightAdjustable(Boolean bool) {
        return spaceRepository.findSpaceByHeightAdjustable(bool);
    }

    public List<Space> getSpacesByFloorIdAndType(Long floor_id, String type, LocalDate date) {
        return spaceRepository.findSpacesByFloor_idAndType(floor_id, type, date);
    }
}
