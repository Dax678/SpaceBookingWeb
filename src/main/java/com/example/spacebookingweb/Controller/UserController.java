package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Database.Entity.User;
import com.example.spacebookingweb.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
    UserService userService;

    //Get user by id
    @GetMapping("/api/getUserById/{id}")
//    @ApiOperation(value = "Get user by ID", response = User.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "User found"),
//            @ApiResponse(code = 404, message = "User not found")
//    })
    @Operation(
            summary = "Pobierz szczegóły użytkownika",
            description = "Pobierz szczegółowe informacje o użytkowniku po numerze identyfikacyjnym",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Szczegóły użytkownika"),
                    @ApiResponse(responseCode = "404", description = "Nie znaleziono użytkownika")
            }
    )
    public ResponseEntity<User> getUserById(
//            @ApiParam(value = "User ID", required = true)
            @PathVariable("id") Long id) {
        System.out.println(id);
        User user = userService.getUserById(id);

        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    //Get user by username
    @GetMapping("/api/getUserByUsername/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        System.out.println(username);
        User user = userService.getUserByUsername(username);

        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    //Adding a user to the DB
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
