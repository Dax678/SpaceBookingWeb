package com.example.spacebookingweb.Database.View;

import com.google.errorprone.annotations.Immutable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Immutable
@Entity
@Table(name = "user_informations_view")
public class UserInformationView {
    @Id
    @Column(name = "user_id")
    private Long user_id;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="email")
    private String email;

    @Column(name="address")
    private String address;

    @Column(name="phone_number")
    private String phone_number;

    @Column(name = "profile_image")
    private String profile_image;
}

//CREATE VIEW user_information_view AS
//SELECT u.user_id, ud.name, ud.surname, u.email, ud.address, ud.phone_number, ud.profile_image
//	FROM public.user u
//	INNER JOIN public.user_details ud
//	ON u.user_id=ud.user_id