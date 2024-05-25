package com.flexiorder.security.config.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginDTO {

    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
    private Collection<? extends GrantedAuthority> authorities;

}
