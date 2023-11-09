package com.example.spacebookingweb.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{6,}$")
    private String password;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @Pattern(regexp="(^$|[0-9]{9})")
    private String phoneNumber;

    @NotBlank
    private String address;

    private Set<String> role;

    public SignupRequest(String username, String password, String email, String name, String surname, String phoneNumber, String address, Set<String> role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
