package com.example.comradegaming.service;


import com.example.comradegaming.entities.User;
import com.example.comradegaming.enums.Used;
import com.example.comradegaming.exceptionHandling.CustomException;
import com.example.comradegaming.jms.Sender;
import com.example.comradegaming.repo.ProductRepo;
import com.example.comradegaming.entities.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    private final ProductRepo repository;
    private final Sender jms;

    public ProductService(ProductRepo repo, Sender jms) {
        this.repository = repo;
        this.jms = jms;
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
            throw new CustomException("Product with ID " + id + " not found!", HttpStatus.NOT_FOUND);
        }
    }

    public void addUserToProduct(User user, Product product) {

        checkIfUserIsPresentInDatabase(user);
        checkIfProductIsPresentInDatabase(product);
        product.addUserToBuyerList(user);
        repository.save(product);
        jms.sendInformation(product);
    }

    public void delete(long id) {
        var found = repository.findById(id);
        if (found.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new CustomException("Product with ID " + id + " not found!", HttpStatus.NOT_FOUND);
        }
    }

    public Iterable<Product> findAll() {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : repository.findAll()) {
            if (product.getUsed() != Used.Yes) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    public void addSellerToProduct(User user, Product product) {

        checkIfUserIsPresentInDatabase(user);
        checkIfProductIsPresentInDatabase(product);

        product.setSeller(user);
        repository.save(product);
    }

    private void checkIfProductIsPresentInDatabase(Product product) {
        if (product == null) {
            throw new CustomException("Product not present in database", HttpStatus.NOT_FOUND);
        }
    }

    private void checkIfUserIsPresentInDatabase(User user) {
        if (user == null) {
            throw new CustomException("User not present in database", HttpStatus.NOT_FOUND);
        }
    }
}
