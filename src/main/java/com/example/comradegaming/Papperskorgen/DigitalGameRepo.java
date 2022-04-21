package com.example.comradegaming.Papperskorgen;

import com.example.comradegaming.entities.DigitalGame;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DigitalGameRepo extends CrudRepository<DigitalGame, Integer> {
}
