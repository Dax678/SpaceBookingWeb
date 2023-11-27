package com.example.spacebookingweb.Service;

import com.example.spacebookingweb.Database.Entity.User;
import com.example.spacebookingweb.Database.View.UserInformationView;
import com.example.spacebookingweb.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List <User> getAllUsers() {
        return userRepository.findAll();
    }
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public Boolean checkUserIsPresent(Long id) {
        return  userRepository.existsById(id);
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<UserInformationView> getUserInformationByUserId(Long id) {
        return userRepository.findUserInformationByUserId(id);
    }
}
