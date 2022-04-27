package com.example.comradegaming.controller;

import com.example.comradegaming.entities.User;
import com.example.comradegaming.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

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
        if (found==null) {
            throw new EntityNotFoundException("Could not find User with name " + name);
        } else {
            return new ResponseEntity<>(found, HttpStatus.OK);
        }
    }

    @PatchMapping("buy/{userID}/{productID}")
    public ResponseEntity<User> purchase(@PathVariable Long userID, @PathVariable int productID) {
        service.buyProduct(productID, userID);
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
        if (found==null) {
            throw new EntityNotFoundException("Could not find User with name " + name);
        } else {
            service.delete(name);

            //Placeholder return message. Implement JSON?
            return "Successfully removed user with name " + name;
        }
    }
}
