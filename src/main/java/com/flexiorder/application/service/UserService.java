package com.flexiorder.application.service;

import com.flexiorder.application.dtos.UserDTO;
import com.flexiorder.application.enums.RoleEnum;
import com.flexiorder.application.model.User;
import com.flexiorder.application.repository.UserRepository;
import com.flexiorder.security.config.dtos.CreateUserDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final ModelMapper mapper;

    public Page<UserDTO> getAll(Pageable pageable) {
        var users = repository.findAll(pageable);

        return users.map(user -> mapper.map(user, UserDTO.class));
    }

    public CreateUserDTO create(CreateUserDTO request) {
        var user = mapper.map(request, User.class);

        user.setPassword(encoder.encode(user.getPassword()));

        if (request.getRoles() != null) {
            List<RoleEnum> roles = new ArrayList<>();
            request.getRoles().forEach(role -> roles.add(RoleEnum.valueOf(role.toString())));
            user.setRoles(roles);
        }

        var response = repository.save(user);

        return mapper.map(response, CreateUserDTO.class);
    }
}
