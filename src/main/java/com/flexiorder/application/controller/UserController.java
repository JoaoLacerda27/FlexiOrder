package com.flexiorder.application.controller;

import com.flexiorder.application.controller.documentation.UserDoc;
import com.flexiorder.application.dtos.UserDTO;
import com.flexiorder.application.service.UserService;
import com.flexiorder.security.config.dtos.CreateUserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
@PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN')")
public class UserController implements UserDoc {
    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreateUserDTO> register(@RequestBody @Valid CreateUserDTO request) {
        var response = service.create(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public Page<UserDTO> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }
}
