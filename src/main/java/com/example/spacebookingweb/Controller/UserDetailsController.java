package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Database.Entity.UserDetails;
import com.example.spacebookingweb.Service.UserDetailsService;
import com.example.spacebookingweb.payload.response.MessageResponse;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
@Validated
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class UserDetailsController {
    UserDetailsService userDetailsService;

    /**
     * @param id the ID of the user
     * @return User details with the given ID
     */
    @GetMapping("/api/user/getDetails/{id}")
    public ResponseEntity<?> getUserDetailsById(@PathVariable("id") @Min(value = 1, message = "ID must be greater than or equal to 1") Long id) {
        Optional<UserDetails> optionalUserDetails = userDetailsService.getUserDetailsById(id);

        if(optionalUserDetails.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalUserDetails);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User details with id: " + id + " not found"));
        }
    }
}
