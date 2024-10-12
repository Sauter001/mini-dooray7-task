package com.nhnacademy.taskapi.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName) {
        super("Resource '" + resourceName + "' not found");
    }
}
