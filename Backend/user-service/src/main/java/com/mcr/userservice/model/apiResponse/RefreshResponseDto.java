package com.mcr.userservice.model.apiResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshResponseDto {
    private String accessToken;
    private String refreshToken;
    private UserDto user;
}
