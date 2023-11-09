package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Configuration.Security.auth.jwt.AuthEntryPointJwt;
import com.example.spacebookingweb.Configuration.Security.auth.jwt.JwtUtils;
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
import com.example.spacebookingweb.payload.response.MessageResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    UserDetailsService userDetailsService;
    PasswordEncoder encoder;
    JwtUtils jwtUtils;
    UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          UserDetailsService userDetailsService, PasswordEncoder encoder,
                          JwtUtils jwtUtils, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        // Sprawdź, czy istnieją użytkownicy o takim samym username lub email
        if (userRepository.existsUserByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsUserByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Tworzenie nowego użytkownika
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        user.setRole(ERole.USER.getRoleNameWithPrefix());

        User savedUser = userService.saveUser(user);

        // Tworzenie i przypisanie szczegółów użytkownika
        UserDetails userDetails = new UserDetails();
        userDetails.setName(signUpRequest.getName());
        userDetails.setSurname(signUpRequest.getSurname());
        userDetails.setPhoneNumber(signUpRequest.getPhoneNumber());
        userDetails.setAddress(signUpRequest.getAddress());
        userDetails.setUserId(savedUser.getId());

        UserDetails savedUserDetails = userDetailsService.saveUserDetails(userDetails);

        savedUser.setUserDetails(savedUserDetails);
        savedUser = userService.saveUser(savedUser);

        if (savedUser != null && savedUserDetails != null) {
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Error saving user."));
        }
    }
}
