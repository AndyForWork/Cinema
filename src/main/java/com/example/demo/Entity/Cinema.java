package com.example.demo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String location;

    private String info;

    @OneToMany(mappedBy = "cinema",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Hall> halls = new ArrayList<>();





}
