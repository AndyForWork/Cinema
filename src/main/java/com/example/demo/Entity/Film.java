package com.example.demo.Entity;

import com.example.demo.Controllers.GenreController;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Data
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

    private Date year;

    private String info;

    //private String img;

    @ManyToOne
    private Genre genre;

    public Film() {
    }

    public Film(String name, Date year, String desc) {
        this.name = name;
        this.year = year;
        this.info = desc;
    }
}
