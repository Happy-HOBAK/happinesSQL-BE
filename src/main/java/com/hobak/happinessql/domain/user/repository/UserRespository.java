package com.hobak.happinessql.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface UserRespository extends JpaRepository<User, Long> {
}
