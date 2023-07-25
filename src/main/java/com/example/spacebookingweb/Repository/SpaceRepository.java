package com.example.spacebookingweb.Repository;

import com.example.spacebookingweb.Database.Entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {

    Optional<Space> findSpaceById(Long id);

    List<Space> findSpaceByHeightAdjustable(Boolean bool);

    List<Space> findSpaceByType(String type);

    @Query(value = "SELECT s FROM Space s " +
            "WHERE NOT EXISTS (SELECT 1 FROM Reservation r WHERE r.space_id=s.id AND r.reservation_date = :date AND r.reservation_status=true) " +
            "AND s.floor_id=:floor_id " +
            "AND s.type=:type")
    List<Space> findSpacesByFloor_idAndType(Long floor_id, String type, LocalDate date);

    //TODO: Filtrowanie wolnych miejsc po dacie -> do kalendarza
}
