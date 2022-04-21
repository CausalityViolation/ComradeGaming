package com.example.comradegaming.Papperskorgen;

import com.example.comradegaming.entities.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepo extends CrudRepository<Movie, Integer> {
}
