package com.example.spacebookingweb.Repository;

import com.example.spacebookingweb.Database.Entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {
    Optional<Floor> findFloorById(Long id);
    Optional<Floor> findFloorByName(String name);
}
