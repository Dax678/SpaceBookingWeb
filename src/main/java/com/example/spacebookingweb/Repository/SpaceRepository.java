package com.example.spacebookingweb.Repository;

import com.example.spacebookingweb.Database.Entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {

    Optional<Space> findSpaceById(Long id);

    List<Space> findSpaceByFloorId(Long id);

    List<Space> findSpaceByIsHeightAdjustable(Boolean bool);

    List<Space> findSpaceByType(String type);

    List<Space> findSpacesByIsAvailable(Boolean bool);

    @Query(value = "SELECT s FROM Space s " +
            "WHERE NOT EXISTS (SELECT 1 FROM Reservation r WHERE r.spaceId=s.id AND r.reservationDate = :date AND r.reservationStatus=:bool) " +
            "AND s.floorId=:floorId " +
            "AND s.type=:type " +
            "AND s.isAvailable=true")
    List<Space> findSpacesByFloor_idAndType(Long floorId, String type, LocalDate date, Boolean bool);

    //TODO: Filtrowanie wolnych miejsc po dacie -> do kalendarza
}
