package com.example.spacebookingweb.IntegrationTests.Auth;

import com.example.spacebookingweb.Configuration.Security.auth.service.UserDetailsImpl;
import com.example.spacebookingweb.Database.Entity.ERole;
import com.example.spacebookingweb.Database.Entity.User;
import com.example.spacebookingweb.Database.Entity.UserDetails;
import com.example.spacebookingweb.Repository.UserRepository;
import com.example.spacebookingweb.Service.UserDetailsService;
import com.example.spacebookingweb.Service.UserService;
import com.example.spacebookingweb.payload.request.LoginRequest;
import com.example.spacebookingweb.payload.request.SignupRequest;
import com.example.spacebookingweb.payload.response.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetailsService userDetailsService;


    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void authenticateUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest("testuser", "Testpassword1");

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("Testpassword1");
        user.setEmail("testemail@test.com");
        user.setRole(ERole.USER.getRoleNameWithPrefix());

        UserDetailsImpl userDetails = UserDetailsImpl.build(user);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        when(userService.getUserByUsername("testuser")).thenReturn(Optional.of(user));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JwtResponse response = objectMapper.readValue(content, JwtResponse.class);

        assertNotNull(response);
        assertEquals("testuser", response.getUsername());
    }

    @Test
    void registerUser() throws Exception {
        SignupRequest signUpRequest = new SignupRequest(
                "testuser",
                "Testpassword1",
                "test@example.com",
                "Name Test",
                "Surname Test",
                "123456789",
                "123 Main St",
                Set.of("USER"));

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("Testpassword1");
        user.setEmail("testemail@test.com");
        user.setRole(ERole.USER.getRoleNameWithPrefix());

        UserDetails userDetails = new UserDetails();

        userDetails.setUserId(1L);
        userDetails.setName("Name Test");
        userDetails.setSurname("Surname Test");
        userDetails.setPhoneNumber("1234567890");
        userDetails.setAddress("123 Main St");

        when(userRepository.existsUserByUsername("testuser")).thenReturn(false);
        when(userRepository.existsUserByEmail("test@example.com")).thenReturn(false);
        when(userService.saveUser(any())).thenReturn(user);
        when(userDetailsService.saveUserDetails(any())).thenReturn(userDetails);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        String expectedResponse = "{\"message\":\"User registered successfully!\",\"object\":{\"id\":1,\"username\":\"testuser\",\"password\":\"Testpassword1\",\"email\":\"testemail@test.com\",\"role\":\"ROLE_USER\"}}";

        assertEquals(expectedResponse, content);
    }

    @Test
    void authenticateUser_Failure() throws Exception {
        LoginRequest loginRequest = new LoginRequest("testuser", "Testpassword1");

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("Testpassword1");
        user.setEmail("testemail@test.com");
        user.setRole(ERole.USER.getRoleNameWithPrefix());

        // Symulacja sytuacji, w której hasło jest nieprawidłowe
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        when(userService.getUserByUsername("testuser")).thenReturn(Optional.of(user));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isInternalServerError())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertEquals("{\"message\":\"Invalid credentials\"}", content);
    }
}