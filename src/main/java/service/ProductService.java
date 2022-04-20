package service;


import entities.DigitalGame;
import entities.Product;
import jdk.jfr.Category;
import repo.ProductRepo;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public class ProductService {

    private final ProductRepo repository;

    public ProductService(ProductRepo repo) {
        this.repository = repo;
    }

    public String add(Product product) {
        repository.save(product);
        //placeholder return message
        return "successfully added";
    }

    public Optional<Product> find(int id) {
        var found = repository.findById(id);
        if (found.isPresent()) {
            return found;
        } else {
            //placeholder return message if fail
            throw new EntityNotFoundException("Product with ID " + id + "not found!");
        }
    }

    public void delete(int id) {
        var found = repository.findById(id);
        if (found.isPresent()) {
            repository.deleteById(id);
        } else {
            //placeholder return message if fail
            throw new EntityNotFoundException("Product with ID " + id + "not found!");
        }
    }

    public Iterable<Product> findAll() {
        return repository.findAll();
    }

    public String getType(Product product) {
        return "You provided me with a " + product.getClass().toString();
    }

}
