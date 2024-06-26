package com.hobak.happinessql.domain.report.application;

import com.hobak.happinessql.domain.record.domain.Record;
import com.hobak.happinessql.domain.record.repository.RecordRepository;
import com.hobak.happinessql.domain.report.dto.ActivityRankingResponseDto;
import com.hobak.happinessql.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportActivityRankingService {

    private final RecordRepository recordRepository;

    public List<ActivityRankingResponseDto> getTop3AllHappiestActivities(User user) {
        List<Record> records = recordRepository.findAllByUser(user);
        return ActivityHappinessAnalyzer.getActivityRankings(records, 3);
    }

    public List<ActivityRankingResponseDto> getTop3AnnualHappiestActivities(User user) {
        int currentYear = LocalDate.now().getYear();
        LocalDateTime startOfYear = LocalDateTime.of(currentYear, 1, 1, 0, 0);
        LocalDateTime endOfYear = LocalDateTime.of(currentYear, 12, 31, 23, 59, 59);
        List<Record> records = recordRepository.findAllByCreatedAtBetweenAndUser(startOfYear, endOfYear, user);
        return ActivityHappinessAnalyzer.getActivityRankings(records, 3);
    }

    public List<ActivityRankingResponseDto> getTop3MonthlyHappiestActivities(User user) {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        LocalDateTime startOfMonthDateTime = startOfMonth.atStartOfDay();
        LocalDateTime endOfMonthDateTime = endOfMonth.atTime(23, 59, 59);
        List<Record> records = recordRepository.findAllByCreatedAtBetweenAndUser(startOfMonthDateTime, endOfMonthDateTime, user);
        return ActivityHappinessAnalyzer.getActivityRankings(records, 3);
    }

    public List<ActivityRankingResponseDto> getAllActivityRankings(User user) {
        List<Record> records = recordRepository.findAllByUser(user);
        return ActivityHappinessAnalyzer.getActivityRankings(records);
    }

    public List<ActivityRankingResponseDto> getYearlyActivityRankings(User user) {
        int currentYear = LocalDate.now().getYear();
        LocalDateTime startOfYear = LocalDateTime.of(currentYear, 1, 1, 0, 0);
        LocalDateTime endOfYear = LocalDateTime.of(currentYear, 12, 31, 23, 59, 59);
        List<Record> records = recordRepository.findAllByCreatedAtBetweenAndUser(startOfYear, endOfYear, user);
        return ActivityHappinessAnalyzer.getActivityRankings(records);
    }

    public List<ActivityRankingResponseDto> getMonthlyActivityRankings(User user) {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate endOfMonth = LocalDate.now().withDayOfMonth(today.lengthOfMonth());
        LocalDateTime startOfMonthDateTime = startOfMonth.atStartOfDay();
        LocalDateTime endOfMonthDateTime = endOfMonth.atTime(23, 59, 59);
        List<Record> records = recordRepository.findAllByCreatedAtBetweenAndUser(startOfMonthDateTime, endOfMonthDateTime, user);
        return ActivityHappinessAnalyzer.getActivityRankings(records);
    }
}
