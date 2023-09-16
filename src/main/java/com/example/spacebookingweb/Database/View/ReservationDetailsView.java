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

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Immutable
@Table(name = "reservation_details_view")
public class ReservationDetailsView {
    @Id
    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "space_name")
    private String spaceName;

    @Column(name = "space_type")
    private String spaceType;

    @Column(name = "is_height_adjustable")
    private Boolean isHeightAdjustable;

    @Column(name = "floor_num")
    private String floorNum;

    @Column(name = "reservation_date", columnDefinition = "timestamp(6) default null")
    private LocalDate reservationDate;

    @Column(name = "reservation_status")
    private Boolean reservationStatus;
}

//    CREATE VIEW reservation_details_view AS
//    SELECT r.id as reservation_id, ud.name, ud.surname, s.name as space_name, s.type as space_type, s.is_height_adjustable, f.floor_num, r.reservation_date, r.reservation_status
//        FROM public.reservation r
//        INNER JOIN public.user u
//        ON u.id = r.user_id
//        INNER JOIN public.space s
//        ON r.space_id=s.id
//        INNER JOIN public.floor f
//        ON s.floor_id=f.id
//        LEFT JOIN user_details ud
//        ON u.id = ud.user_id
