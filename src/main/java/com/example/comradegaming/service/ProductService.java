package com.example.comradegaming.service;


import com.example.comradegaming.entities.User;
import com.example.comradegaming.enums.Used;
import com.example.comradegaming.repo.ProductRepo;
import com.example.comradegaming.entities.Product;
import com.example.comradegaming.repo.UserRepo;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepo repository;
    private final UserRepo userRepo;

    public ProductService(ProductRepo repo, UserRepo userRepo) {
        this.repository = repo;
        this.userRepo = userRepo;
    }

    public String add(Product product) {
        repository.save(product);
        //placeholder return message
        return "successfully added";
    }

    public Optional<Product> find(long id) {
        var found = repository.findById(id);
        if (found.isPresent()) {
            return found;
        } else {
            //placeholder return message if fail
            throw new EntityNotFoundException("Product with ID " + id + "not found!");
        }
    }

    public void addUserToProduct(User user, Product product){
        product.addUserToBuyerList(user);
        repository.save(product);
    }

    public void delete(long id) {
        var found = repository.findById(id);
        if (found.isPresent()) {
            repository.deleteById(id);
        } else {
            //placeholder return message if fail
            throw new EntityNotFoundException("Product with ID " + id + "not found!");
        }
    }

    public Iterable<Product> findAll() {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : repository.findAll()) {
            if (product.getUsed() != Used.YES) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    public String getType(Product product) {
        return "You provided me with a " + product.getClass().toString();
    }

    public void addSellerToProduct(User user, Product product) {
        product.setSeller(user);
        repository.save(product);
    }
}
