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
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters long")
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{6,}$", message = "Password must contain at least one number and one uppercase letter and one lowercase letter.")
    private String password;

    @NotBlank
    @Size(min = 3, max = 50, message = "Username must be between 3 and 20 characters long")
    @Email(message = "Email must be a valid email")
    private String email;

    @NotBlank
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters long")
    private String name;

    @NotBlank
    @Size(min = 2, max = 30, message = "Surname must be between 2 and 30 characters long")
    private String surname;

    @NotBlank
    @Pattern(regexp="(^$|[0-9]{9})", message = "Phone number must be a valid phone number")
    private String phoneNumber;

    @NotBlank
    @Size(min = 5, max = 40, message = "Address must be between 5 and 40 characters long")
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
