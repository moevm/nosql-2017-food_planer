package com.letiproject.foodplanner.app.postgres.repository;

import com.letiproject.foodplanner.app.postgres.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByFirstName(String username);
}