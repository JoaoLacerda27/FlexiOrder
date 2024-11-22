package com.flexiorder.security;

import com.flexiorder.application.model.User;
import com.flexiorder.security.config.dtos.LoginRequestDTO;
import com.flexiorder.security.config.dtos.LoginResponseDTO;
import com.flexiorder.security.config.dtos.UserLoginDTO;
import com.flexiorder.security.config.service.AuthService;
import com.flexiorder.security.jwt.JwtUtils;
import com.flexiorder.shared.exceptions.types.UnauthorizedException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final ModelMapper mapper;
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) throws UnauthorizedException {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.getEmail().trim(), request.getPassword());
        var auth = authenticationManager.authenticate(usernamePassword);

        User user = (User) auth.getPrincipal();

        var accessToken = jwtUtils.generateToken((User) auth.getPrincipal());

        LoginResponseDTO result = new LoginResponseDTO();
        result.setToken(accessToken);
        result.setUser(mapper.map(user, UserLoginDTO.class));


        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(accessToken);

        return ResponseEntity.ok(response);
    }
}