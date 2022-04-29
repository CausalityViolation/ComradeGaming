package com.example.comradegaming.controller;

import com.example.comradegaming.entities.Product;
import com.example.comradegaming.entities.User;
import com.example.comradegaming.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.Path;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService service;

    public UserController(UserService userService) {
        this.service = userService;
    }

    @GetMapping()
    public ResponseEntity<Iterable<User>> findAll() {
        Iterable<User> found = service.findAll();
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @GetMapping("{name}")
    public ResponseEntity<User> findByName(@PathVariable String name) {
        User found = service.find(name);
        if (found == null) {
            throw new EntityNotFoundException("Could not find User with name " + name);
        } else {
            return new ResponseEntity<>(found, HttpStatus.OK);
        }
    }

    @PatchMapping("buy/{userID}/{productID}")
    public ResponseEntity<User> purchase(@PathVariable Long userID, @PathVariable Long productID) {
        service.buyProduct(productID, userID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("forsale/{userID}/add")
    public ResponseEntity<User> sellProduct(@PathVariable Long userID, @RequestBody Product product) {
        service.addForSale(product, userID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("forsale/{userID}/sold/{productID}")
    public ResponseEntity<User> forSaleSold(@PathVariable long userID, @PathVariable long productID){
        service.sellForSale(userID, productID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<User> add(@RequestBody User user) {

        User added = service.add(user);
        return new ResponseEntity<>(added, HttpStatus.CREATED);
    }

    @DeleteMapping("{name}")
    public String remove(@PathVariable String name) {

        User found = service.find(name);
        if (found == null) {
            throw new EntityNotFoundException("Could not find User with name " + name);
        } else {
            service.delete(name);

            //Placeholder return message. Implement JSON?
            return "Successfully removed user with name " + name;
        }
    }

    @GetMapping("forsale/{userID}")
    public Set<Product> deliverForSale(@PathVariable long userID){
        return service.deliverForSale(userID);
    }
}
