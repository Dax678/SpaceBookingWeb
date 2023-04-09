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
    @Column(name = "space_id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "monitor_number")
    private int monitorNumber;

    @Column(name= "height_adjustable")
    private Boolean heightAdjustable;

    @OneToMany(mappedBy = "space")
    @JsonIgnore
    private List<Reservation> reservationList;
}
