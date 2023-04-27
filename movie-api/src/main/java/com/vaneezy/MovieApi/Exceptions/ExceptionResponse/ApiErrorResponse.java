package com.vaneezy.MovieApi.Exceptions.ExceptionResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ApiErrorResponse {

    private LocalDateTime timestamp;
    private String message;

}
