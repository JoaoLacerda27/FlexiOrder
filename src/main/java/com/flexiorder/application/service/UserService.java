package com.flexiorder.application.service;

import com.flexiorder.application.model.User;
import com.flexiorder.application.repository.UserRepository;
import com.flexiorder.security.config.dtos.CreateUserDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final ModelMapper mapper;

    public CreateUserDTO create(CreateUserDTO request) {
        var user = mapper.map(request, User.class);

        user.setPassword(encoder.encode(user.getPassword()));

        var response = repository.save(user);

        return mapper.map(response, CreateUserDTO.class);
    }
}
