package com.example.demo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private boolean bought;

    @NotNull
    private Long cost;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = true)
    private Session session;

    public Seat(boolean bought, Long cost) {
        this.bought = bought;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", bought=" + bought +
                ", cost=" + cost +
                ", session=" + session.getId() +
                '}';
    }
}
