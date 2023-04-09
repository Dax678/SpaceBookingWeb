//package com.example.spacebookingweb.Service;
//
//import com.example.spacebookingweb.Repository.ApplicationUserDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ApplicationUserService implements UserDetailsService {
//    private final ApplicationUserDao applicationUserDao;
//
//    @Autowired
//    public ApplicationUserService(@Qualifier("fake") ApplicationUserDao applicationUserDAO) {
//        this.applicationUserDao = applicationUserDAO;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return applicationUserDao.selectUserByUsername(username)
//                .orElseThrow(() ->
//                        new UsernameNotFoundException(String.format("Username $s not found", username)));
//    }
//}