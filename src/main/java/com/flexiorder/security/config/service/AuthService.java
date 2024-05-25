package com.flexiorder.security.config.service;

import com.flexiorder.application.service.UserDetailsImpl;
import com.flexiorder.security.config.dtos.LoginRequestDTO;
import com.flexiorder.security.config.dtos.LoginResponseDTO;
import com.flexiorder.security.config.dtos.UserLoginDTO;
import com.flexiorder.security.config.jwt.JwtUtils;
import com.flexiorder.shared.exceptions.types.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final ModelMapper mapper;

    public LoginResponseDTO login(LoginRequestDTO request) throws UnauthorizedException {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail().trim(), request.getPassword()));
        var user = (UserDetailsImpl) authentication.getPrincipal();
        var accessToken = jwtUtils.generateToken(user);

        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(accessToken);
        response.setUser(mapper.map(user, UserLoginDTO.class));

        return response;
    }
}
