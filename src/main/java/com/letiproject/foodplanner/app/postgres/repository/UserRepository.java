package com.letiproject.foodplanner.app.postgres.repository;

import com.letiproject.foodplanner.app.postgres.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<User> removeByEmail(String email);

    Optional<User> removeByPhone(String phone);
}