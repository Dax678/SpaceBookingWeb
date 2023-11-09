package com.example.spacebookingweb.Service;

import com.example.spacebookingweb.Database.Entity.ESpace;
import com.example.spacebookingweb.Database.Entity.Space;
import com.example.spacebookingweb.Repository.SpaceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SpaceService {
    SpaceRepository spaceRepository;

    public List<Space> getSpaceList() {
        return spaceRepository.findAll()
                .stream()
                .sorted(Comparator.comparingLong(Space::getId))
                .collect(Collectors.toList());
    }

    public Optional<Space> getSpaceById(Long id) {
        return spaceRepository.findSpaceById(id);
    }

    public Boolean checkSpaceIsPresent(Long id) {
        return spaceRepository.existsById(id);
    }

    public List<Space> getSpaceByFloorId(Long floorId) {
        return spaceRepository.findSpaceByFloorId(floorId)
                .stream()
                .sorted(Comparator.comparingLong(Space::getId))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateSpace(Space space) {
        spaceRepository.save(space);
    }

    public List<Space> getSpacesByIsAvailable(Boolean bool) {
        return spaceRepository.findSpacesByIsAvailable(bool)
                .stream()
                .sorted(Comparator.comparingLong(Space::getId))
                .collect(Collectors.toList());
    }

    public List<Space> getSpaceByType(ESpace type) {
        return spaceRepository.findSpaceByType(type)
                .stream()
                .sorted(Comparator.comparingLong(Space::getId))
                .collect(Collectors.toList());
    }

    public List<Space> getSpaceByIsHeightAdjustable(Boolean bool) {
        return spaceRepository.findSpaceByIsHeightAdjustable(bool)
                .stream()
                .sorted(Comparator.comparingLong(Space::getId))
                .collect(Collectors.toList());
    }

    public List<Space> getSpacesByFloorIdAndType(Long floorId, ESpace type, LocalDate date, Boolean bool) {
        return spaceRepository.findSpacesByFloor_idAndType(floorId, type, date, bool)
                .stream()
                .sorted(Comparator.comparingLong(Space::getId))
                .collect(Collectors.toList());
    }
}
