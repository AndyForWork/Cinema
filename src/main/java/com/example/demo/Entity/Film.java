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

    @NotNull
    private Date year;

    private String info;

    //private String img;

    @ManyToMany
    @JoinTable(
            name = "genre_like",
            joinColumns = @JoinColumn(name="film_id"),
            inverseJoinColumns = @JoinColumn(name="genre_id")
    )
    private List<Genre> genres = new ArrayList<>();



    public void addGenre(Optional<Genre> genre){
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GenreController.class);
        //logger.info(genre.toString());
        genres.add(genre.get());
    }

    public Film() {
    }

    public Film(String name, Date year, String desc) {
        this.name = name;
        this.year = year;
        this.info = desc;
    }
}
