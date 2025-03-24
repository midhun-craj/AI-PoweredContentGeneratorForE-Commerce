package com.mcr.userservice.model.apiResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileResponseDto {
    private long userId;
    private String email;
    private String username;
}
