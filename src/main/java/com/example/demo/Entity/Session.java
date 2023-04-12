package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = true)
    private Hall hall;

    @NotNull
    @ManyToOne
    @JoinColumn(name="film_id", nullable = true)
    private Film film;

    //крч, тут нужна дата + время и все
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy--MM--dd'T'HH:mm")
    private Date startTime;

    @NotNull
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>();

    public Long minCost(){
        Long min = seats.get(0).getCost();
        for (Seat seat: seats){
            if (seat.getCost()<min)
                min = seat.getCost();
        }
        return min;
    }

    public Long maxCost(){
        Long max = seats.get(0).getCost();
        for (Seat seat: seats){
            if (seat.getCost()<max)
                max = seat.getCost();
        }
        return max;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", hall=" + hall.getName() +
                ", film=" + film.getName() +
                ", startTime=" + startTime +
                ", seats=" + seats +
                '}';
    }
}
