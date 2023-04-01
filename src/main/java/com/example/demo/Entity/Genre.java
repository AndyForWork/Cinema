package com.example.demo.Entity;

import com.example.demo.Controllers.GenreController;
import com.example.demo.Repository.FilmRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Genre {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Film> films = new ArrayList<>();

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
