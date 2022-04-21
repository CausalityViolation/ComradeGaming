package com.example.comradegaming.Papperskorgen;

import com.example.comradegaming.entities.Movie;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepo repository;

    public MovieService(MovieRepo movieRepo) {
        this.repository = movieRepo;
    }

    public String add(Movie movie) {
        repository.save(movie);
        //placeholder return message
        return "successfully added";
    }

    public Optional<Movie> find(int id) {
        var found = repository.findById(id);
        if (found.isPresent()) {
            return found;
        } else {
            //placeholder return message if fail
            throw new EntityNotFoundException("Movie with ID " + id + "not found!");
        }
    }

    public void delete(int id) {
        var found = repository.findById(id);
        if (found.isPresent()) {
            repository.deleteById(id);
        } else {
            //placeholder return message if fail
            throw new EntityNotFoundException("Movie with ID " + id + "not found!");
        }
    }

    public Iterable<Movie> findAll() {
        return repository.findAll();
    }

}
