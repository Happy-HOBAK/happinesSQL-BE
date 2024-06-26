package com.hobak.happinessql.domain.user.api;

import com.hobak.happinessql.domain.activity.application.CategoryCreateService;
import com.hobak.happinessql.domain.user.application.CustomUserDetailsService;
import com.hobak.happinessql.domain.user.application.UserFindService;
import com.hobak.happinessql.domain.user.application.UserProfileService;
import com.hobak.happinessql.domain.user.application.UserService;
import com.hobak.happinessql.domain.user.converter.UserConverter;
import com.hobak.happinessql.domain.user.domain.User;
import com.hobak.happinessql.domain.user.dto.*;
import com.hobak.happinessql.global.auth.JwtToken;
import com.hobak.happinessql.global.response.DataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Tag(name="User", description = "유저 관련 REST API에 대한 명세를 제공합니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserProfileService userProfileService;
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final CategoryCreateService categoryCreateService;
    private final UserFindService userFindService;

    @NonNull
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "유저 프로필 조회", description = "유저의 프로필을 조회합니다.")
    @GetMapping("/profile")
    public DataResponseDto<Object> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        UserProfileResponseDto userProfileResponseDto = UserConverter.toUserProfileResponseDto(user);
        return DataResponseDto.of(userProfileResponseDto, "유저 프로필을 성공적으로 조회했습니다.");
    }

    @Operation(summary = "유저 프로필 수정", description = "유저의 프로필을 수정합니다.")
    @PutMapping("/profile")
    public DataResponseDto<Object> updateUserProfile(@RequestBody @Valid UserProfileUpdateRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userFindService.findByUserDetails(userDetails);
        User updatedUser = userProfileService.updateUserProfile(user, requestDto);
        UserProfileResponseDto responseDto = UserConverter.toUserProfileResponseDto(updatedUser);

        return DataResponseDto.of(responseDto, "유저 프로필을 성공적으로 수정했습니다.");
    }

    @Operation(summary = "로그인", description = "사용자가 서비스에 로그인하는 데 사용됩니다")
    @PostMapping("/login")
    public DataResponseDto<JwtToken> signIn(@RequestBody SignInDto signInDto) {
        UserDetails user = customUserDetailsService.loadUserByUsername(signInDto.getUsername());
        if(passwordEncoder.matches(signInDto.getPassword(), user.getPassword())){
            JwtToken jwtToken = userService.signIn(signInDto.getUsername(), signInDto.getPassword());
            return DataResponseDto.of(jwtToken,"로그인이 정상적으로 처리되었습니다.");
        }
        else return DataResponseDto.of(null,"비밀번호가 틀렸습니다.");

    }

    @Operation(summary = "회원가입", description = "사용자가 서비스에 회원가입하는 데 사용됩니다")
    @PostMapping("/sign-up")
    public DataResponseDto<Object> signUp(@RequestBody SignUpDto signUpDto){
        UserDto savedUserDto = userService.signUp(signUpDto);
        categoryCreateService.createCategory(savedUserDto.getUserid());
        return DataResponseDto.of("회원가입이 성공적으로 처리되었습니다.");
    }
}

