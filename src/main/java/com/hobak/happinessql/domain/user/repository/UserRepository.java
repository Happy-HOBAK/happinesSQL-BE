package com.hobak.happinessql.domain.user.repository;

import com.hobak.happinessql.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    User findUserByusername(String username);
}
