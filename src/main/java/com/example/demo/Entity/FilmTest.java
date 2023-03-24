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
public class FilmTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    private Date year;

    private String info;

    //private String img;

    @ManyToOne
    private GenreTest genre;


}
