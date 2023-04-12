package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

    @NotNull
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm:ss")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="HH:mm:s")
    private java.util.Date duration;

    //private String img;

    @ManyToOne
    @JoinColumn(name="genre_id", nullable = true)
    private Genre genre;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Session> sessions = new ArrayList<>();


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
