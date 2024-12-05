package com.flexiorder.application.dtos;

import com.flexiorder.application.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private UUID id;
    private Instant creationDate;
    private Instant lastUpdateDate;
    private String name;
    private String email;
    private String phoneNumber;
    private String document;
    private List<RoleEnum> roles;

}
