package com.vaneezy.MovieApi.DTOs.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.sql.Date;
import java.util.Set;

@Getter
public class MovieRequestDTO {

    @Schema(example = "Bebra")
    @NotNull
    private String title;

    @Schema(description = "value between 0 and 10")
    @NotNull
    private Integer rating;

    @Schema(example = "4otkiy film)")
    private String description;

    @Schema(example = "yyyy-mm-dd")
    @NotNull
    private Date releaseDate;

    @Schema(example = "1")
    @NotNull
    private Long directorId;

    @Schema(example = "[1,2]")
    @NotNull
    private Set<Long> genresId;

}
