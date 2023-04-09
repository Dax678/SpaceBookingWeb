package com.example.spacebookingweb.Repository;

import com.example.spacebookingweb.Database.Entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {

    Space findSpaceById(Long id);

    List <Space> findSpaceByHeightAdjustable(Boolean bool);

    List<Space> findSpaceByType(String type);

    //TODO: Filtrowanie wolnych miejsc po dacie -> do kalendarza
}
