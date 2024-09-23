package com.example.library.core.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entity, String parameter, Object value) {
        super(entity + " not found by " + parameter + " = " + value);
    }
}
