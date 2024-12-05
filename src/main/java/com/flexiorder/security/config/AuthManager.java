package com.flexiorder.security.config;

import com.flexiorder.application.service.UserDetailsImpl;
import com.flexiorder.shared.internationalization.MessageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthManager implements AuthenticationManager {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private MessageProcessor messageProcessor;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication == null) {
            throw new BadCredentialsException(messageProcessor.getMessage("login.invalid.credentials"));
        }

        String username = authentication.getName();
        UserDetailsImpl user = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);

        String password = (String) authentication.getCredentials();

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException(messageProcessor.getMessage("login.invalid.credentials"));
        }


        return new UsernamePasswordAuthenticationToken(user, user.getAuthorities());
    }
}
