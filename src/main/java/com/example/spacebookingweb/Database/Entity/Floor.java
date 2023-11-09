package com.example.spacebookingweb.Database.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
@Entity
@Table(name = "floor", schema = "public")
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "floor_num")
    @NotNull
    private String floorNum;

    @Column(name = "name")
    @NotNull
    @Enumerated(EnumType.STRING)
    private EFloor name;

    @OneToMany(mappedBy = "floor")
    @JsonIgnore
    private List<Space> spaceList;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "floorNum = " + floorNum + ", " +
                "name = " + name + ")";
    }
}
