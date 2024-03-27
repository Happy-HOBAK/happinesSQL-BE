package com.hobak.happinessql.domain.record.repository;

import com.hobak.happinessql.domain.record.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
