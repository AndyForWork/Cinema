package com.example.demo.Repository;

import com.example.demo.Entity.Film;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FilmRepository extends CrudRepository<Film, Long> {
    @Query("SELECT val from Film val WHERE val.name = ?1")
    Film findFilmByName(String name);
}
