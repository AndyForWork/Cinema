package com.example.demo.Entity;

import com.example.demo.Controllers.GenreController;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.juli.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Entity
@Data
public class HallType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String info;

    @OneToMany(mappedBy = "hallType")
    private List<Hall> halls = new ArrayList<>();

    @Override
    public String toString() {
        return "HallType{" +
                "name='" + name + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
