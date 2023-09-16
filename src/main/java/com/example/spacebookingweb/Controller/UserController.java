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
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class UserController {
    UserService userService;
    ReservationService reservationService;

    //Get user by id
    @GetMapping("/{id}")
    @Operation(
            summary = "Get user information by userId",
            description = "Get user information by userId",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User info"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> optionalUser = userService.getUserById(id);

        return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get user information by username",
            description = "Get user information by username",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User info"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping("/name/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        Optional<User> optionalUser = userService.getUserByUsername(username);

        return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get user reservations by userId",
            description = "Get user reservations by userId",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User reservation list"),
                    @ApiResponse(responseCode = "404", description = "User reservations not found")
            }
    )
    @GetMapping("/{id}/reservations")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<UserReservationView>> getUserReservationsList(@PathVariable("id") Long id) {
        List<UserReservationView> reservationList = reservationService.getReservationsByUserId(id);

        if (!reservationList.isEmpty()) {
            return ResponseEntity.ok(reservationList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Get user reservation list by reservation status",
            description = "Get user reservation list by reservation status",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User reservation list"),
                    @ApiResponse(responseCode = "404", description = "User reservations not found")
            }
    )
    @GetMapping("/{id}/reservations/{status}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<UserReservationView>> getUserReservationListByStatus(@PathVariable("id") Long id,
                                                                                  @PathVariable("status") Boolean status) {
        List<UserReservationView> reservationList = reservationService.getUserReservationListByStatus(id, status);

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
    @PostMapping
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
    @GetMapping("/{id}/details")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserInformationView> getUserInformations(@PathVariable("id") Long id) {
        Optional<UserInformationView> userInformationViewOptional = userService.getUserInformationByUserId(id);

        if(userInformationViewOptional.isPresent()) {
            return userInformationViewOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
