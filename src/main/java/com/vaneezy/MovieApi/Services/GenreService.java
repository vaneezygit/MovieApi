package com.vaneezy.MovieApi.Services;

import com.vaneezy.MovieApi.DTOs.Request.GenreRequestDTO;
import com.vaneezy.MovieApi.Entities.Genre;
import com.vaneezy.MovieApi.Exceptions.AlreadyExistsException.GenreAlreadyExistsException;
import com.vaneezy.MovieApi.Exceptions.NotFoundException.GenreNotFoundException;
import com.vaneezy.MovieApi.Repositories.GenreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GenreService {

    private final GenreRepository genreRepository;
    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Set<Genre> findGenresById(Set<Long> genreIds){
        Set<Genre> genres = genreIds.stream()
                .map(genreId -> genreRepository.findById(genreId).orElseThrow(
                        () -> new GenreNotFoundException(
                                String.format("Genre with id %d does not exists", genreId)
                        )
                )).collect(Collectors.toSet());
        return genres;
    }

    public List<Genre> getAll() {
        log.info("Fetching all genres");
        return genreRepository.findAll();
    }

    public Genre addGenre(GenreRequestDTO genre) {
        genreRepository.findGenreByGenreName(genre.getGenreName())
                .ifPresent(
                        g -> {
                            log.error("Trying to insert genre which already exists id: {}", g.getGenreId());
                            throw new GenreAlreadyExistsException(
                                    String.format("Genre with name %s already exists", g.getGenreName())
                            );
                        }
                );
        Genre saved = genreRepository.save(new Genre(genre.getGenreName()));
        log.info("Genre with id {} saved", saved.getGenreId());
        return saved;
    }
}
