package com.example.spacebookingweb.Database.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    @Column(name = "reservation_id")
    private Long id;

    //FK
    @Column(name = "user_id")
    private Long user_id;

    //FK
    @Column(name = "space_id")
    private Long space_id;

    //FK
    @Column(name = "payment_id")
    private Long payment_id;

    //cannot be > 1 day
    @Column(name = "reservation_start_date")
    private LocalDateTime start_date;

    @Column(name = "reservation_end_date")
    private LocalDateTime end_date;

    @JsonIgnore // Ignoruje pole "parent" podczas serializacji
    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id",
            insertable = false, updatable = false)
    private User user;

    @JsonIgnore // Ignoruje pole "parent" podczas serializacji
    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "space_id", referencedColumnName = "space_id",
            insertable = false, updatable = false)
    private Space space;
}
