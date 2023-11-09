package com.example.spacebookingweb.IntegrationTests.Controller;

import com.example.spacebookingweb.Configuration.Security.auth.jwt.JwtUtils;
import com.example.spacebookingweb.Configuration.Security.auth.service.UserDetailsImpl;
import com.example.spacebookingweb.Database.Entity.ERole;
import com.example.spacebookingweb.Database.Entity.Floor;
import com.example.spacebookingweb.Database.Entity.User;
import com.example.spacebookingweb.Service.FloorService;
import com.example.spacebookingweb.Service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FloorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FloorService floorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getFloorList() throws Exception {
        List<Floor> floorList = new ArrayList<>();
        Mockito.when(floorService.getAll()).thenReturn(floorList);

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("Testpassword1");
        user.setEmail("testemail@test.com");
        user.setRole(ERole.USER.getRoleNameWithPrefix());

        UserDetailsImpl userDetails = UserDetailsImpl.build(user);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        String jwtToken = jwtUtils.generateJwtToken(authentication);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/floor")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        List<Floor> response = objectMapper.readValue(content, List.class);

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    void getFloorById() {
    }

    @Test
    void getFloorReservationsByFloorIdAndDate() {
    }
}