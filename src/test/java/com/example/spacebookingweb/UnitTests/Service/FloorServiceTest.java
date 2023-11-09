package com.example.spacebookingweb.UnitTests.Service;

import com.example.spacebookingweb.Database.Entity.EFloor;
import com.example.spacebookingweb.Database.Entity.Floor;
import com.example.spacebookingweb.Repository.FloorRepository;
import com.example.spacebookingweb.Service.FloorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FloorServiceTest {

    @InjectMocks
    private FloorService floorService;

    @Mock
    private FloorRepository floorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFloorById() {
        Long floorId = 1L;

        Floor expectedFloor = new Floor();
        expectedFloor.setId(floorId);
        Mockito.when(floorRepository.findFloorById(floorId)).thenReturn(Optional.of(expectedFloor));

        Optional<Floor> result = floorService.getFloorById(floorId);

        assertTrue(result.isPresent());
        assertEquals(floorId, result.get().getId());
    }

    @Test
    void testCheckFloorIsPresent() {
        Long floorId = 1L;
        Mockito.when(floorRepository.existsById(floorId)).thenReturn(true);

        Boolean result = floorService.checkFloorIsPresent(floorId);

        assertTrue(result);
    }

    @Test
    void testGetFloorByName() {
        EFloor floorName = EFloor.IT;

        Floor expectedFloor = new Floor();
        expectedFloor.setName(floorName);
        Mockito.when(floorRepository.findFloorByName(floorName.name())).thenReturn(Optional.of(expectedFloor));

        Optional<Floor> result = floorService.getFloorByName(floorName.name());

        assertTrue(result.isPresent());
        assertEquals(floorName, result.get().getName());
    }

    @Test
    void TestGetAll() {
        List<Floor> floorList = new ArrayList<>();
        Mockito.when(floorRepository.findAll()).thenReturn(floorList);

        List<Floor> result = floorService.getAll();

        assertNotNull(result);
        assertEquals(floorList, result);
    }
}