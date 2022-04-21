package com.example.comradegaming.controller;

import com.example.comradegaming.entities.*;
import com.example.comradegaming.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping("product")
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

    /*
    @GetMapping("{id}")
    public ResponseEntity<Optional<Product>> find(@PathVariable Integer id) {
        Optional<Product> found = service.find(id);
        checkIfNull(found);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DigitalGame> add(@RequestBody DigitalGame game) {
        service.add(game);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Product> remove(@PathVariable Integer id) {
        checkIfNull(service.find(id));
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }





    //NÅNSLAGSTEMPLATEIDE
    private void checkIfNull(Optional<Product> thing) {
        if (!thing.isPresent()) {
            throw new EntityNotFoundException("Entity not found");
        }
    }
     */
}
