package com.flexiorder.application.service;

import com.flexiorder.application.dtos.UserDTO;
import com.flexiorder.application.enums.RoleEnum;
import com.flexiorder.application.model.User;
import com.flexiorder.application.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;

    @Test
    @DisplayName("Should get all users when everything is OK")
    void findAllUsersCaseSuccess() {
        var pageable = Pageable.unpaged();
        var user = new User("João Vitor", "joao.lacerda@gmail.com", "11988747858", "123");
        var userDto = new UserDTO(
                UUID.randomUUID(),
                Instant.now(),
                Instant.now(),
                "João Vitor",
                "joao.lacerda@gmail.com",
                "11988747858",
                "50714442836",
                List.of(RoleEnum.ROOT)
        );
        var page = new PageImpl<>(List.of(user), pageable, 1);

        when(repository.findAll(pageable)).thenReturn(page);
        when(mapper.map(user, UserDTO.class)).thenReturn(userDto);

        var result = service.getAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("João Vitor", result.getContent().get(0).getName());
        verify(repository, times(1)).findAll(pageable);
        verify(mapper, times(1)).map(user, UserDTO.class);
    }

    @Test
    @DisplayName("Should throw Exception when users not get successfully")
    void findAllUsersCaseFailed() {
        var pageable = Pageable.unpaged();

        when(repository.findAll(pageable)).thenThrow(new RuntimeException("No users found"));

        var exception = assertThrows(RuntimeException.class, () -> service.getAll(pageable));

        assertEquals("No users found", exception.getMessage());
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Should create user successfully when everything is OK")
    void createUserCaseSuccess() {
    }

    @Test
    @DisplayName("Should throw Exception when an user not create successfully")
    void createUserCaseFailed() {
    }
}