package Papperskorgen;

import entities.DigitalGame;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class DigitalGameService {
    private DigitalGameRepo repository;

    public void add(DigitalGame game) {
        //Kanske borde man returnerna något snyggt
        repository.save(game);
    }

    public Optional<DigitalGame> find(Integer id){
        var found = repository.findById(id);
        if (!found.isPresent()) {
            throw new EntityNotFoundException("Game with id " + id + " not found!");
        }
        return found;
    }

    public void delete(Integer id) {

        var found = repository.findById(id);
        if (found.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("User with id " + id + " not found!");
        }
    }

    public Iterable<DigitalGame> findAll() {
        return repository.findAll();
    }
}
