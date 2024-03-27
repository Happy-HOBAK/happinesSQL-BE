package com.hobak.happinessql.domain.record.repository;

import com.hobak.happinessql.domain.record.domain.RecordImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordImageRepository extends JpaRepository<RecordImage, Long> {
}
