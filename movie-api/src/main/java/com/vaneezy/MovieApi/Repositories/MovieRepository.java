package com.vaneezy.MovieApi.Repositories;

import com.vaneezy.MovieApi.Entities.Director;
import com.vaneezy.MovieApi.Entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(
            "SELECT m FROM Movie m WHERE m.title = :title AND m.director = :director"
    )
    boolean exists(@Param("title")String title, @Param("director")Director director);

}
