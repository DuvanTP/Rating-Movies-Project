package com.duvantp.task.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duvantp.task.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
