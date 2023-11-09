package com.example.spacebookingweb.Database.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@RequiredArgsConstructor
@Data
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
    @NotNull
    private Long userId;

    //FK
    @Column(name = "space_id")
    @NotNull
    private Long spaceId;

    //FK
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "reservation_date")
    @NotNull
    private LocalDate reservationDate;

    @Column(name = "reservation_status")
    @NotNull
    private Boolean reservationStatus;

    @Column(name = "entity_creation_date")
    @CreationTimestamp
    private LocalDate entityCreationDate;

    @Column(name = "reservation_update_date")
    @UpdateTimestamp
    private LocalDate reservationUpdateDate;


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

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "userId = " + userId + ", " +
                "spaceId = " + spaceId + ", " +
                "paymentId = " + paymentId + ", " +
                "reservationDate = " + reservationDate + ", " +
                "reservationStatus = " + reservationStatus + ", " +
                "entityCreationDate = " + entityCreationDate + ", " +
                "reservationUpdateDate = " + reservationUpdateDate + ")";
    }
}
