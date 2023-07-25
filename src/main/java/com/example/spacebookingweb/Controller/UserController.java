package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Database.Entity.ERole;
import com.example.spacebookingweb.Database.Entity.User;
import com.example.spacebookingweb.Database.View.UserInformationView;
import com.example.spacebookingweb.Database.View.UserReservationView;
import com.example.spacebookingweb.Service.ReservationService;
import com.example.spacebookingweb.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class UserController {
    UserService userService;
    ReservationService reservationService;

    //Get user by id
    @GetMapping("/api/user/getById/{id}")
    @Operation(
            summary = "Get user information by ID",
            description = "Get user information by ID. It returns ResponseEntity<User>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User info"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> optionalUser = userService.getUserById(id);

        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Get user information by username",
            description = "Get user information by username. It returns ResponseEntity<User>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User info"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping("/api/user/getByUsername/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        Optional<User> optionalUser = userService.getUserByUsername(username);

        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Get user reservations by userId",
            description = "Get user reservations by userId. It returns ResponseEntity<List<Reservation>>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User reservations"),
                    @ApiResponse(responseCode = "404", description = "Reservation not found")
            }
    )
    @GetMapping("/api/user/getReservation/{id}")
    public ResponseEntity<List<UserReservationView>> getReservationByUserId(@PathVariable("id") Long id) {
        List<UserReservationView> reservationList = reservationService.getReservationByUserId(id);

        if (!reservationList.isEmpty()) {
            return ResponseEntity.ok(reservationList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Get user active reservations by userId",
            description = "Get user active reservations by userId. It returns ResponseEntity<List<Reservation>>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User active reservations"),
                    @ApiResponse(responseCode = "404", description = "Reservation not found")
            }
    )
    @GetMapping("/api/user/getActiveReservation/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<UserReservationView>> getActiveReservationByUserId(@PathVariable("id") Long id) {
        List<UserReservationView> reservationList = reservationService.getActiveReservationByUserId(id);

        if (!reservationList.isEmpty()) {
            return ResponseEntity.ok(reservationList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Save user",
            description = "Save user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User saved"),
                    @ApiResponse(responseCode = "404", description = "Error saving user")
            }
    )
    @PostMapping(value = "/api/user/add")
    public ResponseEntity<String> addUser(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, @RequestParam(value = "email") String email) {
        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(ERole.ROLE_USER);

        User savedUser = userService.saveUser(user);
        if (savedUser != null) {
            return ResponseEntity.ok("User saved successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving user.");
        }
    }

    @Operation(
            summary = "Get user active reservations by userId",
            description = "Get user active reservations by userId. It returns ResponseEntity<List<Reservation>>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User active reservations"),
                    @ApiResponse(responseCode = "404", description = "Reservation not found")
            }
    )
    @GetMapping("/api/user/getInformation/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<UserInformationView>> getUserInformationByUserId(@PathVariable("id") Long id) {
        List<UserInformationView> reservationList = userService.getUserInformationByUserId(id);

        if (!reservationList.isEmpty()) {
            return ResponseEntity.ok(reservationList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
