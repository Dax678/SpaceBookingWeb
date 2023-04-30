package com.example.spacebookingweb.Database.View;

import com.google.errorprone.annotations.Immutable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Immutable
@Entity
//@NamedNativeQuery(
//        name = "UserReservationView",
//        query = "SELECT c.id AS customerId, c.name AS customerName, SUM(o.total_price) AS totalSpent " +
//                "FROM customer c " +
//                "INNER JOIN order o ON c.id = o.customer_id " +
//                "GROUP BY c.id",
//        resultSetMapping = "UserReservationViewMapping"
//)
//@SqlResultSetMapping(
//        name = "UserReservationViewMapping",
//        classes = {
//                @ConstructorResult(
//                        targetClass = UserReservationView.class,
//                        columns = {
//                                @ColumnResult(name = "reservation_id", type = Long.class),
//                                @ColumnResult(name = "user_id", type = Long.class),
//                                @ColumnResult(name = "space_name", type = String.class),
//                                @ColumnResult(name = "space_type", type = String.class),
//                                @ColumnResult(name = "height_adjustable", type = Boolean.class),
//                                @ColumnResult(name = "floor_num", type = String.class),
//                                @ColumnResult(name = "start_date", type = LocalDateTime.class)
//                        }
//                )
//        }
//)
@Table(name = "user_reservation_view")
public class UserReservationView {
    @Id
    @Column(name = "reservation_id")
    private Long reservation_id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "name")
    private String space_name;

    @Column(name = "type")
    private String space_type;

    @Column(name = "height_adjustable")
    private Boolean height_adjustable;

    @Column(name = "floor_num")
    private String floor_num;

    @Column(name = "reservation_date", columnDefinition = "timestamp(6) default null")
    private LocalDate reservation_date;
}

//CREATE VIEW user_reservation_view AS
//SELECT reservation_id, user_id, s.name, s.type, s.height_adjustable, f.floor_num, r.reservation_date
//	FROM public.reservation r
//	INNER JOIN public.space s
//	ON r.space_id=s.space_id
//	INNER JOIN public.floor f
//	ON s.floor_id=f.floor_id
