package com.vaneezy.MovieApi.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "genre")
@Getter
@Setter
@NoArgsConstructor
public class Genre {

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long genreId;

    @Column(name = "genre_name")
    private String genreName;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Movie> movies;

}
