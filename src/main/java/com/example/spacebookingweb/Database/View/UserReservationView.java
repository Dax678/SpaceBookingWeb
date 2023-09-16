package com.example.spacebookingweb.Database.View;

import com.google.errorprone.annotations.Immutable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Immutable
@Table(name = "user_reservation_view")
public class UserReservationView {
    @Id
    @Column(name = "id")
    private Long reservationId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name")
    private String spaceName;

    @Column(name = "type")
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

//CREATE VIEW user_reservation_view AS
//SELECT r.id, r.user_id, s.name, s.type, s.is_height_adjustable, f.floor_num, r.reservation_date, r.reservation_status
//	FROM public.reservation r
//	INNER JOIN public.space s
//	ON r.space_id=s.id
//	INNER JOIN public.floor f
//	ON s.floor_id=f.id
