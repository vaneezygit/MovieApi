package com.example.FilmLibrary;

import com.vaneezy.MovieApi.DTOs.Request.MovieRequestDTO;
import com.vaneezy.MovieApi.Entities.Director;
import com.vaneezy.MovieApi.Entities.Genre;
import com.vaneezy.MovieApi.Entities.Movie;
import com.vaneezy.MovieApi.Exceptions.NotFoundException.MovieNotFoundException;
import com.vaneezy.MovieApi.Repositories.MovieRepository;
import com.vaneezy.MovieApi.Services.DirectorService;
import com.vaneezy.MovieApi.Services.GenreService;
import com.vaneezy.MovieApi.Services.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;
    @Mock
    private MovieRepository movieRepository;

    @Mock
    private GenreService genreService;

    @Mock
    private DirectorService directorService;

    private List<Movie> movies;

    private Genre genre1;
    private Genre genre2;
    private Set<Long> genresIds;
    private Set<Genre> genres;
    private Director director;
    private MovieRequestDTO movieRequestDTO;
    private Movie movie1;
    private Movie movie2;

    @BeforeEach
    public void setup() {
        movies = new ArrayList<>();
        genre1 = new Genre();
        genre1.setGenreId(1L);
        genre2 = new Genre();
        genre2.setGenreId(2L);
        genresIds = new HashSet<>(Arrays.asList(1L, 2L));
        genres = new HashSet<>();
        genres.add(genre1);
        genres.add(genre2);
        director = new Director();
        director.setDirectorId(1L);
        movieRequestDTO = new MovieRequestDTO(
                "Bebronautika",
                9,
                "4oten'ko",
                new Date(System.currentTimeMillis()),
                1L,
                genresIds
        );
        movie1 = new Movie(
                1L,
                "Bebra",
                9, new Date(System.currentTimeMillis()),
                "Normalek",
                new Director(),
                new HashSet<>()
        );
        movie2 = new Movie(
                2L,
                "Kinchik",
                5,
                new Date(System.currentTimeMillis()),
                "Gavneco",
                new Director(),
                new HashSet<>()
        );
        movies.add(movie1);
        movies.add(movie2);
    }

    @Test
    public void testGetAllMovies() {
        when(movieRepository.findAll()).thenReturn(movies);
        List<Movie> allMovies = movieService.getAll();
        verify(movieRepository).findAll();
        Assertions.assertEquals(2, allMovies.size());
        Assertions.assertEquals(1L, allMovies.get(0).getMovieId());
    }

    @Test
    public void testAddMovie() {
        when(genreService.findGenresById(genresIds)).thenReturn(genres);
        when(directorService.findDirectorById(director.getDirectorId())).thenReturn(director);
        when(movieRepository.save(any(Movie.class))).thenReturn(new Movie(
                3L,
                movieRequestDTO.getTitle(),
                movieRequestDTO.getRating(),
                movieRequestDTO.getReleaseDate(),
                movieRequestDTO.getDescription(),
                director,
                genres
        ));
        Movie addedMovie = movieService.addMovie(movieRequestDTO);
        verify(genreService, times(1)).findGenresById(genresIds);
        verify(directorService, times(1)).findDirectorById(director.getDirectorId());
        verify(movieRepository, times(1)).save(any(Movie.class));
        Assertions.assertEquals("Bebronautika", addedMovie.getTitle());
        Assertions.assertEquals(3L, addedMovie.getMovieId());
    }

    @Test
    public void testGetMovieById() throws MovieNotFoundException {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie1));
        Movie foundMovie = movieService.getMovieById(1L);
        verify(movieRepository).findById(1L);
        Assertions.assertEquals("Bebra", foundMovie.getTitle());
    }

    @Test
    public void testGetMovieByIdNotFound() {
        when(movieRepository.findById(3L)).thenReturn(Optional.empty());
        Assertions.assertThrows(MovieNotFoundException.class, () -> movieService.getMovieById(3L));
    }

    @Test
    public void testDeleteMovieById() throws MovieNotFoundException {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie1));
        movieService.deleteMovie(1L);
        verify(movieRepository).deleteById(1L);
    }

    @Test
    public void testDeleteMovieByIdNotFound() {
        when(movieRepository.findById(3L)).thenReturn(Optional.empty());
        Assertions.assertThrows(MovieNotFoundException.class, () -> movieService.deleteMovie(3L));
    }

    @Test
    public void testUpdateMovieById() throws MovieNotFoundException {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie1));
        when(genreService.findGenresById(genresIds)).thenReturn(genres);
        when(directorService.findDirectorById(director.getDirectorId())).thenReturn(director);
        when(movieRepository.save(any(Movie.class))).thenReturn(new Movie(
                1L,
                "Bebronautika",
                9,
                new Date(System.currentTimeMillis()),
                "4oten'ko",
                director,
                genres
        ));
        Movie updatedMovie = movieService.updateMovie(1L, movieRequestDTO);
        verify(movieRepository).findById(1L);
        verify(genreService, times(1)).findGenresById(genresIds);
        verify(directorService, times(1)).findDirectorById(director.getDirectorId());
        verify(movieRepository, times(1)).save(any(Movie.class));
        Assertions.assertEquals("Bebronautika", updatedMovie.getTitle());
        Assertions.assertEquals(9, updatedMovie.getRating());
        Assertions.assertEquals("4oten'ko", updatedMovie.getDescription());
    }

    @Test
    public void testUpdateMovieByIdNotFound() {
        when(movieRepository.findById(3L)).thenReturn(Optional.empty());
        Assertions.assertThrows(MovieNotFoundException.class, () -> movieService.updateMovie(3L, movieRequestDTO));
    }
}