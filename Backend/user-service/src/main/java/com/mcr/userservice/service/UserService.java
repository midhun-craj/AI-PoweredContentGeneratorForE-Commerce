package com.mcr.userservice.service;

import com.mcr.userservice.model.User;
import com.mcr.userservice.model.apiRequest.LoginRequest;
import com.mcr.userservice.model.apiRequest.RefreshRequest;
import com.mcr.userservice.model.apiRequest.RegisterRequest;
import com.mcr.userservice.model.apiResponse.LoginResponseDto;
import com.mcr.userservice.model.apiResponse.RefreshResponseDto;
import com.mcr.userservice.model.apiResponse.UserDto;
import com.mcr.userservice.model.apiResponse.UserProfileResponseDto;
import com.mcr.userservice.repository.UserRepository;
import com.mcr.userservice.security.UserDetailsImpl;
import com.mcr.userservice.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use.");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setProvider("LOCAL");
        userRepository.save(user);
    }

    public LoginResponseDto login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        String accessToken = jwtUtil.generateToken(new UserDetailsImpl(user), true);
        String refreshToken = jwtUtil.generateToken(new UserDetailsImpl(user), false);

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new LoginResponseDto(accessToken, refreshToken, new UserDto(user.getId(), user.getEmail(), user.getUsername()));
    }

    public RefreshResponseDto refreshToken(RefreshRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        if(!request.getRefreshToken().equals(user.getRefreshToken())) {
            throw new BadCredentialsException("Invalid refresh token.");
        }

        String accessToken = jwtUtil.generateToken(new UserDetailsImpl(user), true);
        return new RefreshResponseDto(accessToken, request.getRefreshToken(),
                new UserDto(user.getId(), user.getEmail(), user.getUsername()));
    }

    public UserProfileResponseDto getUserProfile(long userId, String email, String username) {

        return new UserProfileResponseDto(userId, email, username);
    }
}
