package com.example.spacebookingweb.Database.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
@Entity
@Table(name = "space", schema = "public")
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "floor_id")
    @NotNull
    private Long floorId;

    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 9)
    private String name;

    @Column(name = "type")
    @NotNull
    @Enumerated(EnumType.STRING)
    private ESpace type;

    @Column(name = "monitor_number")
    @NotNull
    private int monitorNumber;

    @Column(name = "is_height_adjustable")
    @NotNull
    private Boolean isHeightAdjustable;

    @Column(name = "is_available")
    @NotNull
    private Boolean isAvailable;

    @OneToMany(mappedBy = "space")
    @JsonIgnore
    private List<Reservation> reservationList;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "floor_id", referencedColumnName = "id",
            insertable = false, updatable = false)
    private Floor floor;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "floorId = " + floorId + ", " +
                "name = " + name + ", " +
                "type = " + type + ", " +
                "monitorNumber = " + monitorNumber + ", " +
                "isHeightAdjustable = " + isHeightAdjustable + ", " +
                "isAvailable = " + isAvailable + ")";
    }
}
