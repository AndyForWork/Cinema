package com.example.demo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

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

    @OneToMany(mappedBy = "cinema",cascade = CascadeType.REMOVE)
    private List<Hall> halls = new ArrayList<>();
    

    public void addHalls(Hall hall) {
        if (hall.getCinema()!=null)
            hall.getCinema().getHalls().remove(hall);
        halls.add(hall);
    }

    public void changeHalls(List<Hall> halls) {
        for (Hall hall : this.halls) {
            hall.setCinema(null);
        }
        this.halls.clear();
        if (halls!=null) {
            for (Hall hall : halls) {
                if (hall.getCinema() != null && hall.getCinema().getId()!=this.id)
                    hall.getCinema().getHalls().remove(hall);
            }
        }
        for (Hall hall: halls)
            hall.setCinema(this);
        this.halls=halls;
    }

    public void clearHalls() {
        for (Hall hall: halls){
            hall.setCinema(null);
        }
        halls.clear();
    }

    public boolean checkHall(Long id){
        for (Hall hall: halls){
            if (hall.getId()==id)
                return true;
        }
        return false;
    }

}
