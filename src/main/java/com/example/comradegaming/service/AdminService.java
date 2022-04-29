package com.example.comradegaming.service;

import com.example.comradegaming.entities.Product;
import com.example.comradegaming.entities.User;
import com.example.comradegaming.enums.Category;
import com.example.comradegaming.enums.Used;
import com.example.comradegaming.repo.AdminRepo;
import com.example.comradegaming.entities.Admin;
import com.example.comradegaming.repo.ProductRepo;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;

@Service
public class AdminService {

    private final AdminRepo repository;
    private final ProductRepo productRepo;

    public AdminService(AdminRepo adminRepo, ProductRepo productRepo) {
        this.repository = adminRepo;
        this.productRepo = productRepo;
    }

    public Set<User> getBuyers(long productID) {
        Optional<Product> productOptional = productRepo.findById(productID);
        //exceptionhandling här
        Product foundProduct = productOptional.get();
        return foundProduct.getBuyers();
    }

    public String add(Admin admin) {
        repository.save(admin);
        return "successfully added new Admin";
    }

    public Optional<Admin> find(String name) {
        var found = repository.findById(name);
        if (found.isPresent()) {
            return found;
        } else {
            throw new EntityNotFoundException("Admin with name " + name + "not found!");
        }
    }

    public void delete(String name) {

        var found = repository.findById(name);
        if (found.isPresent()) {
            repository.deleteById(name);
        } else {
            throw new EntityNotFoundException("Admin with name " + name + "not found!");
        }
    }

    public Iterable<Admin> findAll() {
        return repository.findAll();
    }

    public Product updateProduct(long productID, Product updatedItem) {
        Optional<Product> optionalProduct = productRepo.findById(productID);

        //exceptionhandling
        Product originalItem = optionalProduct.get();

        if (updatedItem.getPrice() != 0) {
            originalItem.setPrice(updatedItem.getPrice());
        }

        if (updatedItem.getName() != null) {
            originalItem.setName(updatedItem.getName());
        }

        if (updatedItem.getProductDescription() != null) {
            originalItem.setProductDescription(updatedItem.getProductDescription());
        }

        if (updatedItem.getCategory() != null) {
            originalItem.setCategory(updatedItem.getCategory());
        }

        if (updatedItem.getImageURL() != null) {
            originalItem.setImageURL(updatedItem.getImageURL());
        }
        updatedItem.setId(originalItem.getId());
        productRepo.save(originalItem);
        return originalItem;
    }

}
