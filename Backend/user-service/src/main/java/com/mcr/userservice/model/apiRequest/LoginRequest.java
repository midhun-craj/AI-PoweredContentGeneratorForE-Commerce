package com.mcr.userservice.model.apiRequest;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
