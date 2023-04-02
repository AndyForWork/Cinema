package com.example.demo.Repository;

import com.example.demo.Entity.Cinema;
import org.springframework.data.repository.CrudRepository;

public interface CinemaRepository extends CrudRepository<Cinema,Long> {
}
