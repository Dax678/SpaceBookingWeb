package com.example.spacebookingweb.Database.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name= "email",unique = true)
    private String email;

    @Column(name = "role")
    private ERole role;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", referencedColumnName = "user_id")
    private UserDetails userDetails;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Reservation> reservationList;

    public ERole getRole() {
        return role;
    }
}
