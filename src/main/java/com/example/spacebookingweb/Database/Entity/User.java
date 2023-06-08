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
//@SecondaryTable(name="user_details", pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "user_id"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column(unique = true)
    private String email;

    @Column(name = "role")
    private ERole role;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserDetails userDetails;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Reservation> reservationList;

    public ERole getRole() {
        return role;
    }
}
