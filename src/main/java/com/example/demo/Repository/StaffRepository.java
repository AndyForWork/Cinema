package com.example.demo.Repository;

import com.example.demo.Entity.Staff;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StaffRepository extends CrudRepository<Staff, Long> {
    @Query("SELECT u FROM Staff u WHERE u.firstName=?1 AND u.lastName=?2")
    List<Staff> findAllByFullName(String firstName, String lastName);
}
