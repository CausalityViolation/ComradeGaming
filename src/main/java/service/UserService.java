package service;

import entities.User;
import org.springframework.stereotype.Service;
import repo.UserRepo;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo repository;

    public UserService(UserRepo userRepo) {
        this.repository = userRepo;
    }

    public User add(User user) {
        return repository.save(user);
    }

    public Optional<User> find(String name) {
        var found = repository.findById(name);
        if (found.isPresent()) {
            return found;
        } else {
            throw new EntityNotFoundException("User with name " + name + "not found!");
        }
    }

    public void delete(String name) {

        var found = repository.findById(name);
        if (found.isPresent()) {
            repository.deleteById(name);
        } else {
            throw new EntityNotFoundException("User with name " + name + "not found!");
        }
    }

    public Iterable<User> findAll() {
        return repository.findAll();
    }
}
