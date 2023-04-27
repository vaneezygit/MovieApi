package com.vaneezy.MovieApi.Exceptions.NotFoundException;

import lombok.Getter;

@Getter
public class MovieNotFoundException extends RuntimeException{

    private String message;
    public MovieNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
