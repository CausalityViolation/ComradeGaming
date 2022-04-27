package com.example.comradegaming.service;

import com.example.comradegaming.entities.Product;
import com.example.comradegaming.repo.ProductRepo;
import com.example.comradegaming.repo.UserRepo;
import com.example.comradegaming.entities.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo repository;
    private final ProductRepo productRepository;

    public UserService(UserRepo userRepo, ProductRepo productRepo) {
        this.repository = userRepo;
        this.productRepository=productRepo;
    }

    public User add(User user) {
        return repository.save(user);
    }

    public User find(String name) {
        var all = repository.findAll();
        User found=null;

        for (User user: all) {
            if(user.getUsername().equalsIgnoreCase(name)){
                found=user;
                break;
            }
        }
        if (found!=null) {
            return found;
            //Ska man verkligen kasta denna exception på flera ställen?!
        } else {
            throw new EntityNotFoundException("User with name " + name + "not found!");
        }
    }

    public void delete(String name) {

        var found = find(name);

        if (found!=null) {
            repository.deleteById(found.getId());
        } else {
            throw new EntityNotFoundException("User with name " + name + "not found!");
        }
    }

    public Iterable<User> findAll() {
        return repository.findAll();
    }

    public void buy(int productID, long userID){
        Optional<Product> bought = productRepository.findById(productID);
        //exceptionhandling behövs här
        Product foundProduct = bought.get();
        User foundUser = user.get();
        foundUser.purchaseProduct(foundProduct);
        repository.save(foundUser);
    }
}
