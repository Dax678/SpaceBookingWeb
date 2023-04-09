package com.example.spacebookingweb.Service;

import com.example.spacebookingweb.Database.Entity.User;
import com.example.spacebookingweb.Database.Entity.UserDetails;
import com.example.spacebookingweb.Repository.UserDetailsRepository;
import com.example.spacebookingweb.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsService {
    UserDetailsRepository userDetailsRepository;

    public UserDetails getUserDetailsById(Long id) {
        return userDetailsRepository.findUserDetailsById(id);
    }
}
