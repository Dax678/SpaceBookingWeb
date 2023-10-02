package com.example.spacebookingweb.Service;

import com.example.spacebookingweb.Database.Entity.Space;
import com.example.spacebookingweb.Repository.SpaceRepository;
import jakarta.transaction.Transactional;
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

    public List<Space> getSpaceByFloorId(Long floorId) {
        return spaceRepository.findSpaceByFloorId(floorId);
    }

    @Transactional
    public void updateSpaceStatus(Space space) {
        spaceRepository.save(space);
    }

    public List<Space> getSpacesByIsAvailable(Boolean bool) {
        return spaceRepository.findSpacesByIsAvailable(bool);
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
