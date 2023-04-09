package com.example.spacebookingweb.Controller;

import com.example.spacebookingweb.Database.Entity.UserDetails;
import com.example.spacebookingweb.Service.UserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserDetailsController {
    UserDetailsService userDetailsService;

    @GetMapping("/api/getUserDetailsById/{id}")
    public ResponseEntity<UserDetails> getUserDetailsById(@PathVariable("id") Long id) {
        System.out.println(id);
        UserDetails userDetails = userDetailsService.getUserDetailsById(id);

        if(userDetails == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userDetails);
    }
}
