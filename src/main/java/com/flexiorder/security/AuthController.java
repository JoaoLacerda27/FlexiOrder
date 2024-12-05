package com.flexiorder.security;

import com.flexiorder.application.model.User;
import com.flexiorder.security.config.dtos.LoginRequestDTO;
import com.flexiorder.security.config.dtos.LoginResponseDTO;
import com.flexiorder.security.config.dtos.UserLoginDTO;
import com.flexiorder.security.config.service.AuthService;
import com.flexiorder.security.jwt.JwtUtils;
import com.flexiorder.shared.exceptions.types.UnauthorizedException;
import com.flexiorder.shared.internationalization.MessageProcessor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {
    @Autowired
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final ModelMapper mapper;
    private final MessageProcessor messageProcessor;
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) throws UnauthorizedException {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername().trim(), request.getPassword())
            );

            User user = (User) auth.getPrincipal();
            String accessToken = jwtUtils.generateToken(user);

            LoginResponseDTO response = new LoginResponseDTO();
            response.setToken(accessToken);
            response.setUser(mapper.map(user, UserLoginDTO.class));

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            throw new UnauthorizedException(messageProcessor.getMessage("login.invalid.credentials"));
        }
    }
}