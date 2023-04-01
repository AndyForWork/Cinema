package com.example.demo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private Date birth;


    private String mainInfo;


    private String allInfo;


    @ManyToMany(mappedBy = "staffs")
    private List<Film> films = new ArrayList<>();

    public Staff() {
    }

    public Staff(Long id, String firstName, String lastName, Date birth, String mainInfo, String allInfo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth = birth;
        this.mainInfo = mainInfo;
        this.allInfo = allInfo;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birth=" + birth +
                ", mainInfo='" + mainInfo + '\'' +
                ", allInfo='" + allInfo + '\'' +
                '}';
    }

    public boolean isFilmContainsById(Film get_film){
        if (get_film == null)
            return false;
        for (Film film: films){
            if (film.getId()==get_film.getId())
                return true;
        }
        return false;
    }
}
