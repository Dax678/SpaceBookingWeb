//package com.example.spacebookingweb.Configuration.Security.auth;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Set;
//
//@AllArgsConstructor
//@Getter
//@Setter
//@ToString
//public class ApplicationUserDetails implements UserDetails {
//    private final Set<? extends GrantedAuthority> grantedAuthorities;
//    private final String password;
//    private final String username;
//    private final boolean isAccountNonExpired;
//    private final boolean isAccountNonLocked;
//    private final boolean isCredentialsNonExpired;
//    private final boolean isEnabled;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return grantedAuthorities;
//    }
//}