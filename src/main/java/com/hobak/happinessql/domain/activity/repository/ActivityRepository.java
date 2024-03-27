package com.hobak.happinessql.domain.activity.repository;

import com.hobak.happinessql.domain.activity.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
