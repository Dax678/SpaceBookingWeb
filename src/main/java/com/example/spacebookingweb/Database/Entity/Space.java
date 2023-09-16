package com.example.spacebookingweb.Database.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "space", schema = "public")
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "floor_id")
    private Long floorId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "monitor_number")
    private int monitorNumber;

    @Column(name = "is_height_adjustable")
    private Boolean isHeightAdjustable;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @OneToMany(mappedBy = "space")
    @JsonIgnore
    private List<Reservation> reservationList;

    @JsonIgnore // Ignoruje pole "parent" podczas serializacji
    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "floor_id", referencedColumnName = "id",
            insertable = false, updatable = false)
    private Floor floor;

}
