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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "director")
@NoArgsConstructor
@Getter
@Setter
public class Director {

    public Director(String directorName) {
        this.directorName = directorName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "director_id", nullable = false)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long directorId;

    @Column(name = "director_name", nullable = false)
    private String directorName;

    @OneToMany(mappedBy = "director", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Movie> movies;
}
