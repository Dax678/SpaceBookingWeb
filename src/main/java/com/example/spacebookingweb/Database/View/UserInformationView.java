package com.example.spacebookingweb.Database.View;

import com.google.errorprone.annotations.Immutable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Immutable
@Table(name = "user_information_view")
public class UserInformationView {
    @Id
    @Column(name = "id")
    private Long userId;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="email")
    private String email;

    @Column(name="address")
    private String address;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name = "profile_image")
    private String profileImage;
}

//CREATE VIEW user_information_view AS
//SELECT u.id, ud.name, ud.surname, u.email, ud.address, ud.phone_number, ud.profile_image
//	FROM public.user u
//	INNER JOIN public.user_details ud
//	ON u.id=ud.user_id