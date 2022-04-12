package controller;

import entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService service;

    public UserController(UserService userService) {
        this.service = userService;
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> findAll() {
        Iterable<User> found = service.findAll();
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @GetMapping("{name}")
    public ResponseEntity<Optional<User>> findByName(@PathVariable String name) {
        Optional<User> found = service.find(name);
        if (found.isEmpty()) {
            throw new EntityNotFoundException("Could not find User with name " + name);
        } else {
            return new ResponseEntity<>(found, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<User> add(@RequestBody User user) {
        User added = service.add(user);
        return new ResponseEntity<>(added, HttpStatus.CREATED);
    }

    @DeleteMapping("{name}")
    public String remove(@PathVariable String name) {

        Optional<User> found = service.find(name);
        if (found.isEmpty()) {
            throw new EntityNotFoundException("Could not find User with name " + name);
        } else {
            service.delete(name);

            //Placeholder return message. Implement JSON?
            return "Successfully removed user with name " + name;
        }
    }
}
