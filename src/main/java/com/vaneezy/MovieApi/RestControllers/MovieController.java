package com.vaneezy.MovieApi.RestControllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vaneezy.MovieApi.Entities.Genre;
import com.vaneezy.MovieApi.Entities.Movie;
import com.vaneezy.MovieApi.DTOs.Request.MovieRequestDTO;
import com.vaneezy.MovieApi.Exceptions.ExceptionResponse.ApiErrorResponse;
import com.vaneezy.MovieApi.Services.MovieService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    @Operation(summary = "Fetch all genres")
    @ApiResponse(responseCode = "200", description = "success",
    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class))})
    public ResponseEntity<List<Movie>> getAllMovies(){
        List<Movie> movies = movieService.getAll();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{movieId}")
    @Operation(summary = "Update movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated movie",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class))}),
            @ApiResponse(responseCode = "404", description = "movie not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    public ResponseEntity<Movie> movieById(@PathVariable("movieId") Long movieId){
        Movie movie = movieService.getMovieById(movieId);
        return ResponseEntity.ok(movie);
    }
    @PostMapping(consumes = "application/json")
    @Operation(summary = "Insert movie")
    @ApiResponse(responseCode = "201", description = "success",
    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class))})
    public ResponseEntity<Movie> postMovie(@Valid @RequestBody MovieRequestDTO movieDTO){
        Movie newMovie = movieService.addMovie(movieDTO);
        return new ResponseEntity<>(newMovie, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{movieId}", consumes = "application/json")
    @Operation(summary = "Update movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "updated movie",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class))}),
            @ApiResponse(responseCode = "404", description = "movie not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    public ResponseEntity<Movie> updateMovie(@PathVariable("movieId") Long movieId,
                                             @RequestBody MovieRequestDTO movieDTO){
        Movie updatedMovie = movieService.updateMovie(movieId, movieDTO);
        return new ResponseEntity<>(updatedMovie, HttpStatus.CREATED);
    }

    @DeleteMapping("/{movieId}")
    @Operation(summary = "Delete movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "delete message",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "movie not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    public ResponseEntity<?> deleteMovie(@PathVariable("movieId") Long movieId){
        movieService.deleteMovie(movieId);
        Map<String, String> response = Map.of(
                "message", "DELETED"
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
