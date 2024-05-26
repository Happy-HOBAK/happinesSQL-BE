package com.hobak.happinessql.domain.report.api;


import com.hobak.happinessql.domain.report.application.ReportRankingService;
import com.hobak.happinessql.domain.report.application.ReportSummaryService;
import com.hobak.happinessql.domain.report.dto.ActivityHappinessDto;
import com.hobak.happinessql.domain.report.dto.ReportSummaryResponseDto;
import com.hobak.happinessql.domain.user.application.UserFindService;
import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.global.response.DataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="Report", description = "행복 분석 리포트 관련 REST API에 대한 명세를 제공합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/report")
public class ReportController {

    private final UserFindService userFindService;
    private final ReportSummaryService reportSummaryService;
    private final ReportRankingService reportRankingService;
    @Operation(summary = "[전체] 행복 종합 리포트", description = "전체 기간에서 언제, 어디에서, 무엇을 할 때 행복했는지에 대한 종합적인 리포트를 제공합니다.")
    @GetMapping("/all/summary")
    public DataResponseDto<ReportSummaryResponseDto> getAllSummary(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        ReportSummaryResponseDto responseDto = reportSummaryService.getAllSummary(user);
        return DataResponseDto.of(responseDto, "행복 종합 리포트(전체)를 성공적으로 조회했습니다.");
    }

    @Operation(summary = "[연간] 행복 종합 리포트", description = "이번 해 언제, 어디에서, 무엇을 할 때 행복했는지에 대한 종합적인 리포트를 제공합니다.")
    @GetMapping("/year/summary")
    public DataResponseDto<ReportSummaryResponseDto> getAnnualSummary(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        ReportSummaryResponseDto responseDto = reportSummaryService.getAnnualSummary(user);
        return DataResponseDto.of(responseDto, "행복 종합 리포트(연간)를 성공적으로 조회했습니다.");
    }

    @Operation(summary = "[월간] 행복 종합 리포트", description = "이번 달 언제, 어디에서, 무엇을 할 때 행복했는지에 대한 종합적인 리포트를 제공합니다.")
    @GetMapping("/month/summary")
    public DataResponseDto<ReportSummaryResponseDto> getMonthlySummary(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        ReportSummaryResponseDto responseDto = reportSummaryService.getMonthlySummary(user);
        return DataResponseDto.of(responseDto, "행복 종합 리포트(월간)를 성공적으로 조회했습니다.");
    }

    @Operation(summary = "[전체] 행복도가 높은 Top 3 활동", description = "전체 기록에서 가장 행복도가 높은 Top 3 활동을 제공합니다.")
    @GetMapping("/all/top-activities")
    public DataResponseDto<List<ActivityHappinessDto>> getTop3AllHappiestActivities(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        List<ActivityHappinessDto> responseDto = reportRankingService.getTop3AllHappiestActivities(user);
        return DataResponseDto.of(responseDto, "행복도가 높은 Top 3 활동(전체)을 성공적으로 조회했습니다.");
    }

    @Operation(summary = "[연간] 행복도가 높은 Top 3 활동", description = "이번 해 가장 행복도가 높은 Top 3 활동을 제공합니다.")
    @GetMapping("/year/top-activities")
    public DataResponseDto<List<ActivityHappinessDto>> getTop3AnnualHappiestActivities(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        List<ActivityHappinessDto> responseDto = reportRankingService.getTop3AnnualHappiestActivities(user);
        return DataResponseDto.of(responseDto, "행복도가 높은 Top 3 활동(연간)을 성공적으로 조회했습니다.");
    }

    @Operation(summary = "[월간] 행복도가 높은 Top 3 활동", description = "이번 해 가장 행복도가 높은 Top 3 활동을 제공합니다.")
    @GetMapping("/month/top-activities")
    public DataResponseDto<List<ActivityHappinessDto>> getTop3MonthlyHappiestActivities(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        List<ActivityHappinessDto> responseDto = reportRankingService.getTop3MonthlyHappiestActivities(user);
        return DataResponseDto.of(responseDto, "행복도가 높은 Top 3 활동(월간)을 성공적으로 조회했습니다.");
    }



}