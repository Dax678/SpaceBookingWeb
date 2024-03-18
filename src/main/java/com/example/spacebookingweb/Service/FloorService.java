package com.example.spacebookingweb.Service;

import com.example.spacebookingweb.Database.Entity.EFloor;
import com.example.spacebookingweb.Database.Entity.Floor;
import com.example.spacebookingweb.Repository.FloorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FloorService {
    private final FloorRepository floorRepository;

    public Optional<Floor> getFloorById(Long id) {
        return floorRepository.findFloorById(id);
    }

    public Boolean checkFloorIsPresent(Long id) {
        return  floorRepository.existsById(id);
    }

    public Optional<Floor> getFloorByName(String type) {
        return floorRepository.findFloorByName(EFloor.valueOf(type));
    }

    public List<Floor> getAll() {
        return floorRepository.findAll();
    }
}
