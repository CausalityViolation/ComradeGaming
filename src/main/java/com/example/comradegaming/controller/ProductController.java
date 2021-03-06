package com.example.comradegaming.controller;

import com.example.comradegaming.entities.*;
import com.example.comradegaming.enums.Category;
import com.example.comradegaming.exceptionHandling.CustomException;
import com.example.comradegaming.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("products")
public class ProductController {


    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }


    @GetMapping("/information/{id}")
    public ResponseEntity<String> getInformation(@PathVariable Long id) {
        Optional<Product> foundItem = service.find(id);
        checkIfNull(foundItem);
        //noinspection OptionalGetWithoutIsPresent
        return new ResponseEntity<>(foundItem.get().getInformation(), HttpStatus.OK);
    }

    private void checkIfNull(Optional<Product> thing) {
        if (thing.isEmpty()) {
            throw new CustomException("Entity not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<Optional<Product>> find(@PathVariable Long id) {
        Optional<Product> found = service.find(id);
        checkIfNull(found);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }


    @PostMapping("/add/digitalgame")
    public ResponseEntity<DigitalGame> add(@RequestBody DigitalGame game) {
        service.add(game);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping("/add/clothing")
    public ResponseEntity<Clothing> add(@RequestBody Clothing cloth) {service.add(cloth);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/add/boardgame")
    public ResponseEntity<BoardGame> add(@RequestBody BoardGame board) {service.add(board);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/add/movie")
    public ResponseEntity<Movie> add(@RequestBody Movie movieFilm) {service.add(movieFilm);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Product> remove(@PathVariable Long id) {
        Optional<Product> productOptional = service.find(id);
        checkIfNull(productOptional);
        //exceptionhandling
        Product foundProduct = productOptional.get();
        Set<User> buyers = foundProduct.getBuyers();
        for (User buyer: buyers) {
            for (Product owned: buyer.getOwned()) {
                if(owned.getId()==foundProduct.getId()){
                    buyer.removePurchasedProduct(owned);
                }
            }
        }
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
