package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Database.Entity.UserDetails;
import com.example.spacebookingweb.Service.UserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class UserDetailsController {
    UserDetailsService userDetailsService;

    @Operation(
            summary = "Get user details by ID",
            description = "Get user details by ID. It returns ResponseEntity<UserDetails>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User details"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping("/api/user/getDetails/{id}")
    public ResponseEntity<UserDetails> getUserDetailsById(@PathVariable("id") Long id) {
        Optional<UserDetails> optionalUserDetails = userDetailsService.getUserDetailsById(id);

        return optionalUserDetails.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
