package com.flexiorder.security.config.service;

import com.flexiorder.application.model.User;
import com.flexiorder.application.repository.UserRepository;
import com.flexiorder.application.service.UserDetailsImpl;
import com.flexiorder.shared.internationalization.MessageProcessor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {
    @Autowired
    private final UserRepository repository;
    private final MessageProcessor messageProcessor;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(messageProcessor.getMessage("user.not.found")));

        return user;
    }
}
