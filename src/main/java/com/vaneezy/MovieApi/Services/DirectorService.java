package com.vaneezy.MovieApi.Services;

import com.vaneezy.MovieApi.DTOs.Request.DirectorRequestDTO;
import com.vaneezy.MovieApi.Entities.Director;
import com.vaneezy.MovieApi.Exceptions.AlreadyExistsException.DirectorAlreadyExistsException;
import com.vaneezy.MovieApi.Exceptions.NotFoundException.DirectorNotFoundException;
import com.vaneezy.MovieApi.Repositories.DirectorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DirectorService {

    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }


    public List<Director> getAll() {
        return directorRepository.findAll();
    }

    public Director addDirector(DirectorRequestDTO directorDTO) {
        directorRepository.findDirectorByDirectorName(directorDTO.getDirectorName()).ifPresent(
                dir -> {
                    log.info("Trying to insert director which already exists id: {}", dir.getDirectorId());
                    throw new DirectorAlreadyExistsException(
                            String.format("Director with name %s already exists", dir.getDirectorName())
                    );
                }
        );
        Director saved = directorRepository.save(new Director(directorDTO.getDirectorName()));
        log.info("Director with id {} saved", saved.getDirectorId());
        return saved;

    }

    public Director findDirectorById(Long directorId) {
        return directorRepository.findById(directorId).orElseThrow(
                () -> new DirectorNotFoundException(
                        String.format("Director with id %d does not exist", directorId)
                )
        );
    }
}
