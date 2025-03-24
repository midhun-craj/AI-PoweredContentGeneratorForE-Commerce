package com.mcr.userservice.controller;

import com.mcr.userservice.model.apiRequest.LoginRequest;
import com.mcr.userservice.model.apiRequest.RefreshRequest;
import com.mcr.userservice.model.apiRequest.RegisterRequest;
import com.mcr.userservice.model.apiResponse.LoginResponseDto;
import com.mcr.userservice.model.apiResponse.RefreshResponseDto;
import com.mcr.userservice.model.apiResponse.UserDto;
import com.mcr.userservice.model.apiResponse.UserProfileResponseDto;
import com.mcr.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "User registered successfully."));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequest request) {
        LoginResponseDto response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponseDto> refresh(@RequestBody RefreshRequest request) {
        RefreshResponseDto response = userService.refreshToken(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponseDto> getUserProfile(
            @RequestHeader("X-User-Id") long userId,
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Username") String username) {
        UserProfileResponseDto userProfile = userService.getUserProfile(userId, email, username);

        return ResponseEntity.ok(userProfile);
    }
}
