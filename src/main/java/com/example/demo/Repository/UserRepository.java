package com.example.demo.Repository;

import com.example.demo.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Client, Long> {
    Client findByName(String name);
}
