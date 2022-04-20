package Papperskorgen;

import entities.DigitalGame;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Papperskorgen.DigitalGameService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping("digital")
public class DigitalGameController {
    private final DigitalGameService service;

    public DigitalGameController(DigitalGameService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<DigitalGame>> find(@PathVariable Integer id) {
        Optional<DigitalGame> foundGame = service.find(id);
        checkIfNull(foundGame);
        return new ResponseEntity<>(foundGame, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DigitalGame> add(@RequestBody DigitalGame game) {
        service.add(game);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DigitalGame> remove(@PathVariable Integer id) {
        checkIfNull(service.find(id));
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<DigitalGame>> findAll(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }


    //NÅNSLAGSTEMPLATEIDE
    public void checkIfNull(Optional<DigitalGame> thing) {
        if (!thing.isPresent()) {
            throw new EntityNotFoundException("Entity not found");
        }
    }


/*



 */
}
