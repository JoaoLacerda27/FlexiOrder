package com.flexiorder.shared.exceptions.types;

import lombok.Getter;

public class UnauthorizedException extends Exception {

    private static final long serialVersionUID = 1L;
    @Getter
    private final String message;

    public UnauthorizedException(String message) {
        this.message = message;
    }

}
