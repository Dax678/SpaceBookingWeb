package com.example.spacebookingweb.IntegrationTests.Controller;

import com.example.spacebookingweb.Configuration.Security.auth.jwt.JwtUtils;
import com.example.spacebookingweb.Configuration.Security.auth.service.UserDetailsImpl;
import com.example.spacebookingweb.Controller.FloorController;
import com.example.spacebookingweb.Database.Entity.ERole;
import com.example.spacebookingweb.Database.Entity.Floor;
import com.example.spacebookingweb.Database.Entity.User;
import com.example.spacebookingweb.Service.FloorService;
import com.example.spacebookingweb.Service.ReservationService;
import com.example.spacebookingweb.Service.UserService;
import com.example.spacebookingweb.payload.request.LoginRequest;
import com.example.spacebookingweb.payload.response.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FloorControllerTest {

    private String jwtToken;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private FloorService floorService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getFloorList() throws Exception {
        List<Floor> floorList = new ArrayList<>();
        Mockito.when(floorService.getAll()).thenReturn(floorList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/floor")
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
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