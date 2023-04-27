package com.vaneezy.MovieApi.Repositories;

import com.vaneezy.MovieApi.Entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findGenreByGenreName(String genreName);

}
