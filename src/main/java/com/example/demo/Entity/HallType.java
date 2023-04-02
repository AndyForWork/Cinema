package com.example.demo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class HallType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String info;

    @OneToMany(mappedBy = "hallType", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Hall> halls = new ArrayList<>();
}
