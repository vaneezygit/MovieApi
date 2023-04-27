package com.vaneezy.MovieApi.Exceptions.ExceptionResponse;

import com.vaneezy.MovieApi.Exceptions.AlreadyExistsException.DirectorAlreadyExistsException;
import com.vaneezy.MovieApi.Exceptions.AlreadyExistsException.GenreAlreadyExistsException;
import com.vaneezy.MovieApi.Exceptions.NotFoundException.DirectorNotFoundException;
import com.vaneezy.MovieApi.Exceptions.NotFoundException.GenreNotFoundException;
import com.vaneezy.MovieApi.Exceptions.NotFoundException.MovieNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({DirectorAlreadyExistsException.class, GenreAlreadyExistsException.class})
    public ResponseEntity<ApiErrorResponse> alreadyExistsExceptionHandler(Exception ex, WebRequest webRequest){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                LocalDateTime.now(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiErrorResponse);
    }

    @ExceptionHandler({MovieNotFoundException.class, DirectorNotFoundException.class, GenreNotFoundException.class})
    public ResponseEntity<ApiErrorResponse> notFoundExceptionHandler(Exception ex, WebRequest webRequest){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                LocalDateTime.now(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorResponse);
    }

}
