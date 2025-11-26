package com.weise.api.repository;

import com.weise.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    // Spring Data: cria o SQL "SELECT * FROM users WHERE email = ?"
    Optional<User> findByEmail(String email);
}