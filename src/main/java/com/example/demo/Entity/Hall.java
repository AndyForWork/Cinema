package com.example.demo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = true)
    private HallType hallType;

    @NotNull
    private int seatsInRow;

    @NotNull
    private int rows;


    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = true)
    private Cinema cinema;


    private void addHallType(HallType hallType){
        this.hallType = hallType;
        hallType.getHalls().add(this);
    }

    private void removeHallType(){
        if (hallType!=null){
            hallType.getHalls().remove(this);
            hallType=null;
        }
    }

    public void changeHallType(HallType hallType){
        removeHallType();
        addHallType(hallType);
    }

}
