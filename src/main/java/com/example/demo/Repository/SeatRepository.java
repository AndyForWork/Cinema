package com.example.demo.Repository;

import com.example.demo.Entity.Seat;
import org.springframework.data.repository.CrudRepository;

public interface SeatRepository extends CrudRepository<Seat, Long> {
}
