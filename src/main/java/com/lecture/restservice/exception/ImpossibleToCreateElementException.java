package com.lecture.restservice.exception;

public class ImpossibleToCreateElementException extends RuntimeException {
    private static final String message = "No gardens yet " +
            "OR no garden with such id exists " +
            "OR gardenId is not indicated " +
            "OR this geoLocation is busy. " +
            "Impossible to create the element";

    public ImpossibleToCreateElementException() {
        super(message);
    }
}
