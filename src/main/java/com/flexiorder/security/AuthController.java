package com.flexiorder.security;

import com.flexiorder.security.config.dtos.LoginRequestDTO;
import com.flexiorder.security.config.dtos.LoginResponseDTO;
import com.flexiorder.security.config.service.AuthService;
import com.flexiorder.shared.exceptions.types.UnauthorizedException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) throws UnauthorizedException {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.login(request));
    }
}