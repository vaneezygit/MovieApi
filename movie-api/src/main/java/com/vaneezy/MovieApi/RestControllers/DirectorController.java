package com.vaneezy.MovieApi.RestControllers;

import com.vaneezy.MovieApi.DTOs.Request.DirectorRequestDTO;
import com.vaneezy.MovieApi.Entities.Director;
import com.vaneezy.MovieApi.Exceptions.ExceptionResponse.ApiErrorResponse;
import com.vaneezy.MovieApi.Services.DirectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/directors")
public class DirectorController {

    private final DirectorService directorService;

    @Autowired
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    @Operation(summary = "Fetch all directors")
    @ApiResponse(responseCode = "200", description = "success",
    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Director.class))})
    public ResponseEntity<List<Director>> getAll(){
        List<Director> directors = directorService.getAll();
        return ResponseEntity.ok(directors);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "success",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Director.class))}),
            @ApiResponse(responseCode = "404", description = "director not found",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "director already exists",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    public ResponseEntity<Director> addDirector(@RequestBody DirectorRequestDTO directorDTO){
        Director newDirector = directorService.addDirector(directorDTO);
        return new ResponseEntity<>(newDirector, HttpStatus.CREATED);
    }
}
