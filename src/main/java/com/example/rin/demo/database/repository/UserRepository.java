package com.example.rin.demo.database.repository;

import com.example.rin.demo.database.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    Boolean existsByEmail(String email);
    User findByPassword(String code);
}
