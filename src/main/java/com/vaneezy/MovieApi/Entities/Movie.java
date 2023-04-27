package com.vaneezy.MovieApi.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movie")
@Getter
@Setter
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id", nullable = false)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long movieId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "release_date", nullable = false)
    private Date releaseDate;

    @Column(name = "description", nullable = true)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id")
    @JsonIgnoreProperties({"movies", "hibernateLazyInitializer"})
    private Director director;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_genre",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
    )
    private Set<Genre> genres;

}
