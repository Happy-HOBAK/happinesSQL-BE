package com.hobak.happinessql.domain.record.api;


import com.hobak.happinessql.domain.record.application.RecordCreateService;
import com.hobak.happinessql.domain.record.application.RecordPagingService;
import com.hobak.happinessql.domain.record.converter.RecordConverter;
import com.hobak.happinessql.domain.record.dto.RecordCreateRequestDto;
import com.hobak.happinessql.domain.record.dto.RecordCreateResponseDto;
import com.hobak.happinessql.domain.record.dto.RecordResponseDto;
import com.hobak.happinessql.domain.user.domain.Gender;
import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.domain.user.repository.UserRepository;
import com.hobak.happinessql.global.response.DataResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/records")
public class RecordController {

    private final RecordCreateService recordCreateService;
    private final RecordPagingService recordPagingService;
    private final UserRepository userRepository;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DataResponseDto<Object> createRecord(
            @Valid @RequestPart(value="content") RecordCreateRequestDto requestDto,
            @RequestPart(required = false) MultipartFile img
        ) {

        // TODO: 임시값 : 로그인 / 로그아웃 구현 시 수정
        // 한 번 실행 시 임시 유저가 만들어지므로 1회 실행 후에는 이 부분은 주석 처리하고 실행하면 됩니다.
        System.out.println(requestDto.getMemo());
        User user = User.builder()
                .username("hobak")
                .password("happy")
                .name("사그미")
                .age(22)
                .gender(Gender.FEMALE)
                .build();
        User newUser = userRepository.save(user);
        System.out.println("userId : " + newUser.getUserId());

        Long recordId = recordCreateService.createRecord(newUser.getUserId(), requestDto, img);
        RecordCreateResponseDto recordCreationResponseDto = RecordConverter.toRecordCreateResponseDto(recordId);

        return DataResponseDto.of(recordCreationResponseDto, "행복 기록이 저장되었습니다.");
    }

    @GetMapping
    public DataResponseDto<Object> getRecordList(@RequestParam Long lastRecordId, @RequestParam int size) {
        // TODO : 임시값 -> 로그인한 유저의 id를 찾아내는 로직으로 변경
        Long userId = 1L;
        List<RecordResponseDto> responseDtos = recordPagingService.fetchRecordPagesBy(lastRecordId, size, userId);
        return DataResponseDto.of(responseDtos, "행복 기록을 성공적으로 조회했습니다.");
    }





}
