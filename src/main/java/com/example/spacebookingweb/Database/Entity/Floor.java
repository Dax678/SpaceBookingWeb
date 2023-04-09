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
@Table(name = "floor", schema = "public")
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "floor_id")
    private Long id;

    @Column(name = "floor_num")
    private String floor_num;

    //IT, HR, Finance, Parking - 4 types
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "floor")
    @JsonIgnore
    private List<Space> spaceList;
}
