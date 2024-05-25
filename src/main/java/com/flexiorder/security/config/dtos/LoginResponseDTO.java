package com.flexiorder.security.config.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {

    private String token;

    private UserLoginDTO user;

    public LoginResponseDTO(String token) {
        this.token = token;
    }
}
