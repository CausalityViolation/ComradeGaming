package Papperskorgen;

import entities.BoardGame;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardGameRepo extends CrudRepository<BoardGame, Integer> {
}
