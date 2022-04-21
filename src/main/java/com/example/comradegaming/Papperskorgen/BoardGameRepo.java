package com.example.comradegaming.Papperskorgen;

import com.example.comradegaming.entities.BoardGame;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardGameRepo extends CrudRepository<BoardGame, Integer> {
}
