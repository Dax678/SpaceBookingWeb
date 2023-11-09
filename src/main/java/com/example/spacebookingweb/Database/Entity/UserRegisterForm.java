package com.example.spacebookingweb.Database.Entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class UserRegisterForm {
    @NotNull
    @Size(min = 3, max = 12)
    private String username;

    @NotNull
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{6,}$")
    private String password;

    @Email
    private String email;
}
