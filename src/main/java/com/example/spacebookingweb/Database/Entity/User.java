package com.example.spacebookingweb.Database.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Entity
@Data
@Table(name = "user", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(min = 3, max = 20)
    @Column(name = "username", unique = true)
    @NotBlank
    private String username;

    @Column(name = "password")
    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{6,}$")
    private String password;

    @Column(name= "email",unique = true)
    @Email
    private String email;

    @Column(name = "role")
    private String role;

    @OneToOne(cascade = CascadeType.ALL) // Dodaj kaskadową operację
    @JoinColumn(name = "user_details_id") // Odnoś się do kolumny user_details_id
    private UserDetails userDetails;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Reservation> reservationList;

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "username = " + username + ", " +
                "password = " + password + ", " +
                "email = " + email + ", " +
                "role = " + role + ", " +
                "userDetails = " + userDetails + ")";
    }
}
