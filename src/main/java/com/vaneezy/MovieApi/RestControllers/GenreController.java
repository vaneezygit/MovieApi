package com.vaneezy.MovieApi.RestControllers;

import com.vaneezy.MovieApi.DTOs.Request.GenreRequestDTO;
import com.vaneezy.MovieApi.Entities.Director;
import com.vaneezy.MovieApi.Entities.Genre;
import com.vaneezy.MovieApi.Exceptions.ExceptionResponse.ApiErrorResponse;
import com.vaneezy.MovieApi.Services.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/genres")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    @Operation(summary = "Fetch all genres")
    @ApiResponse(responseCode = "200", description = "success",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Genre.class))})
    public ResponseEntity<List<Genre>> getAll(){
        List<Genre> genres = genreService.getAll();
        return ResponseEntity.ok().body(genres);
    }

    @PostMapping(consumes = "application/json")
    @Operation(summary = "Insert movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Genre.class))}),
            @ApiResponse(responseCode = "409", description = "genre already exists",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    public ResponseEntity<Genre> addGenre(@RequestBody GenreRequestDTO genre){
        Genre newGenre = genreService.addGenre(genre);
        return new ResponseEntity<>(newGenre, HttpStatus.CREATED);
    }
}
