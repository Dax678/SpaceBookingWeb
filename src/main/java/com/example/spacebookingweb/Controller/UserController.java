package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Database.Entity.Floor;
import com.example.spacebookingweb.Database.Entity.Reservation;
import com.example.spacebookingweb.Database.Entity.User;
import com.example.spacebookingweb.Database.View.UserReservationView;
import com.example.spacebookingweb.Service.ReservationService;
import com.example.spacebookingweb.Service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {
    UserService userService;
    ReservationService reservationService;

    //Get user by id
    @GetMapping("/api/getUserById/{id}")
    @Operation(
            summary = "Get user information by ID",
            description = "Get user information by ID. It returns ResponseEntity<User>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User info"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    public ResponseEntity<User> getUserById(
//            @ApiParam(value = "User ID", required = true)
            @PathVariable("id") Long id) {
        System.out.println(id);
        User user = userService.getUserById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @Operation(
            summary = "Get user information by username",
            description = "Get user information by username. It returns ResponseEntity<User>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User info"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping("/api/getUserByUsername/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        System.out.println(username);
        User user = userService.getUserByUsername(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @Operation(
            summary = "Get user reservations by userId",
            description = "Get user reservations by userId. It returns ResponseEntity<List<Reservation>>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User reservations"),
                    @ApiResponse(responseCode = "404", description = "Reservation not found")
            }
    )
    @GetMapping("/api/getReservationByUserId/{id}")
    public ResponseEntity<List<UserReservationView>> getReservationByUserId(@PathVariable("id") Long id) {
        List<UserReservationView> reservationList = reservationService.getReservationByUserId(id);

        if (reservationList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(reservationList);
    }

    @Operation(
            summary = "Get user active reservations by userId",
            description = "Get user active reservations by userId. It returns ResponseEntity<List<Reservation>>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User active reservations"),
                    @ApiResponse(responseCode = "404", description = "Reservation not found")
            }
    )
    @GetMapping("/api/getActiveReservationByUserId/{id}")
    public ResponseEntity<List<UserReservationView>> getActiveReservationByUserId(@PathVariable("id") Long id) {
        List<UserReservationView> reservationList = reservationService.getActiveReservationByUserId(id);

        if (reservationList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(reservationList);
    }

    @Operation(
            summary = "Save user",
            description = "Save user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User saved"),
                    @ApiResponse(responseCode = "404", description = "Error saving user")
            }
    )
    @PostMapping("/api/addUser")
    public ResponseEntity<String> addUser(User user) {
        User savedUser = userService.saveUser(user);
        if (savedUser != null) {
            return ResponseEntity.ok("User saved successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving user.");
        }
    }
}
