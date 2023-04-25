package com.example.spacebookingweb.Database.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class UserRegisterForm {
    private String username;

    private String password;

    private String email;
}
