package com.example.spacebookingweb.Service;

import com.example.spacebookingweb.Database.Entity.UserDetails;
import com.example.spacebookingweb.Repository.UserDetailsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsService {
    UserDetailsRepository userDetailsRepository;

    public Optional<UserDetails> getUserDetailsById(Long id) {
        return userDetailsRepository.findUserDetailsByUserId(id);
    }

    public Boolean checkUserDetailsIsPresent(Long id) {
        return  userDetailsRepository.existsById(id);
    }

    @Transactional
    public UserDetails saveUserDetails(UserDetails userDetails) {
        return userDetailsRepository.save(userDetails);
    }
}
