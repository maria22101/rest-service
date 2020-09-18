package com.lecture.restservice.exception;

public class NoSuchElementForUpdateException extends RuntimeException {
    private static final String message = "No such element available thus update is impossible";

    public NoSuchElementForUpdateException() {
        super(message);
    }
}
