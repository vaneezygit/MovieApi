package com.vaneezy.MovieApi.Repositories;

import com.vaneezy.MovieApi.Entities.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {

    Optional<Director> findDirectorByDirectorName(String directorName);

}
