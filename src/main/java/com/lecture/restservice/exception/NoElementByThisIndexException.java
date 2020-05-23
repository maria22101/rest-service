package com.lecture.restservice.exception;

public class NoElementByThisIndexException extends RuntimeException {
    private static final String message = "No element by such index found";

    public NoElementByThisIndexException() {
        super(message);
    }
}
