package com.lecture.restservice.exception;

public class NoElementByThisIdException extends RuntimeException {
    private static final String message = "No element by such id found";

    public NoElementByThisIdException() {
        super(message);
    }
}
