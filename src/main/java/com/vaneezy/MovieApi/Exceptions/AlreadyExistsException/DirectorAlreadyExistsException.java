package com.vaneezy.MovieApi.Exceptions.AlreadyExistsException;

public class DirectorAlreadyExistsException extends RuntimeException{

    private String message;

    public DirectorAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

}
