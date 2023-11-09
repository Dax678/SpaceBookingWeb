package com.example.spacebookingweb.Database.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user_details", schema = "public")
public class UserDetails {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column
    @NotNull
    @Size(min = 2, max = 10)
    private String name;
    @Column
    @NotNull
    @Size(min = 2, max = 12)
    private String surname;
    @Column
    @NotNull
    @Size(min = 5, max = 40)
    private String address;
    @Column
    @NotNull
    @Pattern(regexp="(^$|[0-9]{9})")
    private String phoneNumber;
    @Column(name = "profile_image")
    private String profileImage;

    @OneToOne(mappedBy = "userDetails")
    private User user; // Odnoś się do user, a nie do user_id
}
