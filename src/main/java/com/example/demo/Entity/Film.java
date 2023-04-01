package com.example.demo.Entity;

import com.example.demo.Controllers.GenreController;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.commons.logging.LogFactory;
import org.apache.juli.logging.Log;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

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
    @JoinColumn(name="genre_id", nullable = true)
    private Genre genre;

    public Film() {
    }

    public Film(String name, Date year, String desc) {
        this.name = name;
        this.year = year;
        this.info = desc;
    }

    @ManyToMany()
    @JoinTable(name="film-staff",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name="staff_id"))
    private List<Staff> staffs = new ArrayList<>();

    public void addStaff(Staff staff){
        staffs.add(staff);
        staff.getFilms().add(this);
    }

    public void removeStaff(Staff staff){
        staffs.remove(staff);
        staff.getFilms().remove(this);
    }

    public void changeStaff(List<Staff> staff){
        if (staffs!=null) {
            for (Staff prev : staffs) {
                prev.getFilms().remove(this);
            }
        }
        staffs.clear();
        for (Staff newStaff: staff){
            staffs.add(newStaff);
            newStaff.getFilms().add(this);
        }
    }

    public void addGenre(Genre new_genre){
        genre = new_genre;
        new_genre.getFilms().add(this);
    }

    public void removeGenre(){
        if (genre!=null)
            genre.getFilms().remove(this);
        genre = null;
    }

    public void changeGenre(Genre new_genre){
        removeGenre();
        addGenre(new_genre);
    }



    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", info='" + info + '\'' +
                ", genre=" + genre +
                ", staffs=" + staffs +
                '}';
    }
}
