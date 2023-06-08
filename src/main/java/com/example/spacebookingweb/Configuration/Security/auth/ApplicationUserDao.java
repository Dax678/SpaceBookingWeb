package com.example.spacebookingweb.Configuration.Security.auth;

import com.example.spacebookingweb.Configuration.Security.auth.ApplicationUserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ApplicationUserDao {
    Optional<ApplicationUserDetails> selectUserByUsername(String username)
        throws UsernameNotFoundException;
}
