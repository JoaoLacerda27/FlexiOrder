package com.flexiorder.security.config.dtos;

import com.flexiorder.application.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserDTO {
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private String document;
    private List<RoleEnum> roles;
}
