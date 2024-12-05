package com.flexiorder.application.controller.documentation;

import com.flexiorder.application.dtos.UserDTO;
import com.flexiorder.security.config.dtos.CreateUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "User")
public interface UserDoc {

    @Operation(summary = "Create a new user")
    ResponseEntity<CreateUserDTO> register(CreateUserDTO request);
    @Operation(summary = "Get All users")
    Page<UserDTO> getAll(Pageable pageable);
}
