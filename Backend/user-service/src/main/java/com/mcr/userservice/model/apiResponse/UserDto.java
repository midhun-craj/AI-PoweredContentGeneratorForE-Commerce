package com.mcr.userservice.model.apiResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private long id;
    private String email;
    private String username;
}
