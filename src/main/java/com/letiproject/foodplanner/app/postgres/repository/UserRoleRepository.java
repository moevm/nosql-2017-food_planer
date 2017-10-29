package com.letiproject.foodplanner.app.postgres.repository;

import com.letiproject.foodplanner.app.postgres.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}