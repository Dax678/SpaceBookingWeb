package com.example.spacebookingweb.Service;

import com.example.spacebookingweb.Database.Entity.Space;
import com.example.spacebookingweb.Repository.SpaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SpaceService {
    SpaceRepository spaceRepository;

    public List<Space> getSpaceList() {
        return spaceRepository.findAll();
    }

    public Optional<Space> getSpaceById(Long id) {
        return spaceRepository.findSpaceById(id);
    }

    public List<Space> getSpaceByType(String type) {
        return spaceRepository.findSpaceByType(type);
    }

    public List<Space> getSpaceByIsHeightAdjustable(Boolean bool) {
        return spaceRepository.findSpaceByIsHeightAdjustable(bool);
    }

    public List<Space> getSpacesByFloorIdAndType(Long floorId, String type, LocalDate date, Boolean bool) {
        return spaceRepository.findSpacesByFloor_idAndType(floorId, type, date, bool);
    }
}
