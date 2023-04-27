package com.vaneezy.MovieApi.Services;

import com.vaneezy.MovieApi.Entities.Movie;
import com.vaneezy.MovieApi.Exceptions.NotFoundException.MovieNotFoundException;
import com.vaneezy.MovieApi.Repositories.MovieRepository;
import com.vaneezy.MovieApi.DTOs.Request.MovieRequestDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MovieService {
    
    private final MovieRepository movieRepository;
    private final GenreService genreService;
    private final DirectorService directorService;
    
    @Autowired
    public MovieService(MovieRepository movieRepository, GenreService genreService, DirectorService directorService){
        this.movieRepository = movieRepository;
        this.genreService = genreService;
        this.directorService = directorService;
    }

    public List<Movie> getAll() {
        log.info("Fetching all movies");
         return movieRepository.findAll();
    }

    public Movie addMovie(MovieRequestDTO movieDTO) {
        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setRating(movieDTO.getRating());
        movie.setDescription(movieDTO.getDescription());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setGenres(
                genreService.findGenresById(movieDTO.getGenresId())
        );
        movie.setDirector(
                directorService.findDirectorById(movieDTO.getDirectorId())
        );
        Movie saved = movieRepository.save(movie);

        log.info("Movie with id {} is saved", saved.getMovieId());

        return saved;
    }

    @Transactional
    public Movie updateMovie(Long movieId, MovieRequestDTO movieDTO) {
        Movie movieToUpdate = movieRepository.findById(movieId)
                .orElseThrow(
                        () -> {
                            log.error("Movie with id {} does not exist", movieId);
                            throw new MovieNotFoundException(
                                    String.format("Movie with id %d does not exist", movieId)
                            );
                        }
                );
        if(movieDTO.getTitle() != null) movieToUpdate.setTitle(movieDTO.getTitle());

        if(movieDTO.getRating() != null) movieToUpdate.setRating(movieDTO.getRating());

        if(movieDTO.getReleaseDate() != null) movieToUpdate.setReleaseDate(movieDTO.getReleaseDate());

        if(movieDTO.getDescription() != null) movieToUpdate.setDescription(movieDTO.getDescription());

        if(movieDTO.getDirectorId() != null) movieToUpdate.setDirector(
                directorService.findDirectorById(movieDTO.getDirectorId())
        );

        if(movieDTO.getGenresId() != null) movieToUpdate.getGenres().addAll(
                genreService.findGenresById(movieDTO.getGenresId())
        );

        Movie updated = movieRepository.save(movieToUpdate);
        log.info("Updating movie with id {}", movieId);
        return updated;
    }

    @Transactional
    public void deleteMovie(Long movieId) {
        movieRepository.findById(movieId)
                .orElseThrow(
                        () -> {
                            log.error("Movie with id {} does not exist", movieId);
                            throw new MovieNotFoundException(
                                    String.format("Movie with id %d does not exist", movieId)
                                    );
                        }
                );

        movieRepository.deleteById(movieId);
        log.info("Movie with id {} deleted", movieId);
    }

    public Movie getMovieById(Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(
                        () -> new MovieNotFoundException(
                                String.format("Movie with id %d does not exist", movieId)
                        )
                );
    }
}
