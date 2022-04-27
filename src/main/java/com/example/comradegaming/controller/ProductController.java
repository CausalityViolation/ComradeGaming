package com.example.comradegaming.controller;

import com.example.comradegaming.entities.*;
import com.example.comradegaming.enums.Category;
import com.example.comradegaming.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;


@RestController
@RequestMapping("products")
public class ProductController {


    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }


    @GetMapping("/information/{id}")
    public ResponseEntity<String> getInformation(@PathVariable Integer id) {
        Optional<Product> foundItem = service.find(id);
        checkIfNull(foundItem);
        //noinspection OptionalGetWithoutIsPresent
        return new ResponseEntity<>(foundItem.get().getInformation(), HttpStatus.OK);
    }

    private void checkIfNull(Optional<Product> thing) {
        //funkar detta?
        if (thing.isEmpty()) {
            throw new EntityNotFoundException("Entity not found");
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<Optional<Product>> find(@PathVariable Integer id) {
        Optional<Product> found = service.find(id);
        checkIfNull(found);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }


    @PostMapping("/add/digitalgame")
    public ResponseEntity<DigitalGame> add(@RequestBody DigitalGame game) {
        game.setCategory(Category.DigitalGame);

        service.add(game);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping("/add/clothing")
    public ResponseEntity<Clothing> add(@RequestBody Clothing cloth) {
        cloth.setCategory(Category.Clothing);

        service.add(cloth);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/add/boardgame")
    public ResponseEntity<BoardGame> add(@RequestBody BoardGame board) {
        board.setCategory(Category.Board);

        service.add(board);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/add/movie")
    public ResponseEntity<Movie> add(@RequestBody Movie movieFilm) {
        movieFilm.setCategory(Category.Movie);

        service.add(movieFilm);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Product> remove(@PathVariable Integer id) {
        checkIfNull(service.find(id));
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }










/*

    //NÅNSLAGSTEMPLATEIDE
    private void checkIfNull(Optional<Product> thing) {
        if (!thing.isPresent()) {
            throw new EntityNotFoundException("Entity not found");
        }
    }
     */
}
