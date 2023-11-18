package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Database.Entity.ERole;
import com.example.spacebookingweb.Database.Entity.User;
import com.example.spacebookingweb.Database.View.UserInformationView;
import com.example.spacebookingweb.Database.View.UserReservationView;
import com.example.spacebookingweb.Service.ReservationService;
import com.example.spacebookingweb.Service.UserService;
import com.example.spacebookingweb.payload.response.MessageResponse;
import com.example.spacebookingweb.payload.response.ObjectMessageResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    UserService userService;
    ReservationService reservationService;

    /**
     * @param id the ID of the user
     * @return User with the given ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable("id") @Min(value = 1, message = "ID must be greater than or equal to 1") Long id) {
        Optional<User> optionalUser = userService.getUserById(id);

        if(optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalUser.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User with id: + " + id + " not found."));
        }
    }

    /**
     * @param username the username of the user
     * @return User with the given username
     */
    @GetMapping("/name/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username) {
        Optional<User> optionalUser = userService.getUserByUsername(username);

        if(optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalUser.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User with username: " + username + " not found."));
        }
    }

    /**
     * @param id the ID of the user
     * @return The list of the reservations of the user with the given ID
     */
    @GetMapping("/{id}/reservations")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getUserReservationsList(@PathVariable("id") @Min(value = 1, message = "ID must be greater than or equal to 1") Long id) {
        if(!userService.checkUserIsPresent(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User with id: " + id + " not found."));

        List<UserReservationView> reservationList = reservationService.getReservationsByUserId(id);

        return ResponseEntity.status(HttpStatus.OK).body(reservationList);
    }

    /**
     * @param id the ID of the user
     * @param status the status of the reservation
     * @return The list of the reservations of the user with the given ID and status
     */
    @GetMapping("/{id}/reservations/{status}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getUserReservationListByStatus(@PathVariable("id") @Min(value = 1, message = "ID must be greater than or equal to 1") Long id,
                                                            @PathVariable("status")  @NotNull @Pattern(regexp = "true|false", message = "Status should be true or false") String status) {
        if(!userService.checkUserIsPresent(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User with id: " + id + " not found."));

        List<UserReservationView> reservationList = reservationService.getUserReservationListByStatus(id, Boolean.valueOf(status));

        return ResponseEntity.status(HttpStatus.OK).body(reservationList);
    }

    /**
     * @param username the username of the user
     * @param password the password of the user
     * @param email the email of the user
     * @return Saved user with the message response
     */
    @PostMapping
    public ResponseEntity<?> addUser(@RequestParam(value = "username") String username,
                                          @RequestParam(value = "password") String password,
                                          @RequestParam(value = "email") @Email String email) {
        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(ERole.USER.getRoleNameWithPrefix());

        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ObjectMessageResponse<User>("User has been successfully added.", userService.saveUser(user)));
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving user.");
        }
    }

    /**
     * @param id the ID of the user
     * @return Details of the user with the given ID
     */
    @GetMapping("/{id}/details")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getUserInformations(@PathVariable("id") @Min(value = 1, message = "ID must be greater than or equal to 1") Long id) {
        if(!userService.checkUserIsPresent(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User with id: " + id + " not found."));

        Optional<UserInformationView> userInformationViewOptional = userService.getUserInformationByUserId(id);

        if(userInformationViewOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(userInformationViewOptional);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User informations not found."));
        }
    }
}
