package com.example.demo.Repository;

import com.example.demo.Entity.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Long> {
}
