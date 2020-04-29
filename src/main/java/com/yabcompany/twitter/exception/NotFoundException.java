package com.yabcompany.twitter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom 404 page exception
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    protected String message = "Cant find: ";

    public NotFoundException(String entity) {
        super(entity);
        this.message += entity;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
