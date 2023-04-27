package com.vaneezy.MovieApi.Exceptions.NotFoundException;

import lombok.Getter;

@Getter
public class GenreNotFoundException extends RuntimeException{

    private String message;

    public GenreNotFoundException(String message) {
        super(message);
        this.message = message;
    }

}
