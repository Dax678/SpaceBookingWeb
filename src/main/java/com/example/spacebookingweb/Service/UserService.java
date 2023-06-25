package com.example.spacebookingweb.Service;

import com.example.spacebookingweb.Database.Entity.User;
import com.example.spacebookingweb.Database.Entity.UserDetails;
import com.example.spacebookingweb.Database.View.UserInformationView;
import com.example.spacebookingweb.Database.View.UserReservationView;
import com.example.spacebookingweb.Repository.UserDetailsRepository;
import com.example.spacebookingweb.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;

    public List <User> getAllUsers() {
        return userRepository.findAll();
    }
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User getUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<UserInformationView> getUserInformationByUserId(Long id) {
        return userRepository.findUserInformationByUserId(id);
    }
}
