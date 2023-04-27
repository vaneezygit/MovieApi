package com.vaneezy.MovieApi.Exceptions.AlreadyExistsException;

public class GenreAlreadyExistsException extends RuntimeException{

    private String message;

    public GenreAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }
}
