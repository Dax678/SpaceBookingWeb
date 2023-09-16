package com.example.spacebookingweb.Database.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "reservation", schema = "public")
public class Reservation {
    //PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //FK
    @Column(name = "user_id")
    private Long userId;

    //FK
    @Column(name = "space_id")
    private Long spaceId;

    //FK
    @Column(name = "payment_id")
    private Long paymentId;

    //cannot be > 1 day
    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @Column(name = "reservation_status")
    private Boolean reservationStatus;

    @JsonIgnore // Ignoruje pole "parent" podczas serializacji
    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id",
            insertable = false, updatable = false)
    private User user;

    @JsonIgnore // Ignoruje pole "parent" podczas serializacji
    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "space_id", referencedColumnName = "id",
            insertable = false, updatable = false)
    private Space space;
}
