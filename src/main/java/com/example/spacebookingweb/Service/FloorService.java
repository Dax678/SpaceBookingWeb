package com.example.spacebookingweb.Service;

import com.example.spacebookingweb.Database.Entity.Floor;
import com.example.spacebookingweb.Database.Entity.Reservation;
import com.example.spacebookingweb.Repository.FloorRepository;
import com.example.spacebookingweb.Repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FloorService {
    FloorRepository floorRepository;

    public Floor getFloorById(Long id) {
        return floorRepository.findFloorById(id);
    }

    public List<Floor> getFloorsByDepartment(String type) {
        return floorRepository.findFloorsByDepartment(type);
    }
}
