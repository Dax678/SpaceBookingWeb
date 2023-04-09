package com.example.spacebookingweb.Service;

import com.example.spacebookingweb.Database.Entity.Floor;
import com.example.spacebookingweb.Database.Entity.Space;
import com.example.spacebookingweb.Repository.FloorRepository;
import com.example.spacebookingweb.Repository.SpaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SpaceService {
    SpaceRepository spaceRepository;

    public Space getSpaceById(Long id) {
        return spaceRepository.findSpaceById(id);
    }

    public List<Space> getSpaceByType(String type) {
        return spaceRepository.findSpaceByType(type);
    }

    public List<Space> getSpaceByHeightAdjustable(Boolean bool) {
        return spaceRepository.findSpaceByHeightAdjustable(bool);
    }
}
