package com.flexiorder.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.flexiorder.application.model.User;
import com.flexiorder.shared.exceptions.types.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtUtils {
    @Value("${api.security.token.secret-key}")
    private String secret_key;
    @Value("${api.security.token.expiration-time}")
    private Integer expiration_time;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret_key);
            String token = JWT.create()
                    .withIssuer("flexiorder-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException e){
            throw new RuntimeException("Error while generating token", e);
        }
    }

    public String validateToken(String token) throws UnauthorizedException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret_key);
            return JWT.require(algorithm)
                    .withIssuer("flexiorder-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new UnauthorizedException("Invalid token");
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(expiration_time).toInstant(ZoneOffset.of("-03:00"));
    }

}
