package com.vaneezy.MovieApi.Exceptions.NotFoundException;

import lombok.Getter;

@Getter
public class DirectorNotFoundException extends RuntimeException{

    private String message;

    public DirectorNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
