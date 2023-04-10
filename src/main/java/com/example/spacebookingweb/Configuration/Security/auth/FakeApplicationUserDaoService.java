package com.example.spacebookingweb.Configuration.Security.auth;

import com.example.spacebookingweb.Database.Entity.User;
import com.example.spacebookingweb.Repository.ApplicationUserDao;
import com.example.spacebookingweb.Repository.UserRepository;
import com.example.spacebookingweb.Service.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder, UserRepository userRepository, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public Optional<ApplicationUserDetails> selectUserByUsername(String username) {
        return getUsers().stream()
                .filter(applicationUserDetails -> username.equals(applicationUserDetails.getUsername()))
                .findFirst();
    }

    private List<ApplicationUserDetails> getUsers() {
        List<ApplicationUserDetails> userList = Lists.newArrayList();
        for(User user : userService.getAllUsers())
            userList.add(
                    new ApplicationUserDetails(ApplicationUserRole.valueOf(userService.getUserByUsername(user.getUsername()).getRole()).getGrantedAuthorities(),
                            passwordEncoder.encode(user.getPassword()),
                            user.getUsername(),
                            true,
                            true,
                            true,
                            true));
        return userList;
    }
}
