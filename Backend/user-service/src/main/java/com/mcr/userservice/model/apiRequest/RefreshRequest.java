package com.mcr.userservice.model.apiRequest;

import lombok.Data;

@Data
public class RefreshRequest {
    private long userId;
    private String refreshToken;
}
