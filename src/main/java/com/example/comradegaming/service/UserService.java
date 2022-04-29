package com.example.comradegaming.service;

import com.example.comradegaming.entities.Product;
import com.example.comradegaming.enums.Used;
import com.example.comradegaming.exceptionHandling.CustomException;
import com.example.comradegaming.repo.ProductRepo;
import com.example.comradegaming.repo.UserRepo;
import com.example.comradegaming.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepo repository;
    private final ProductRepo productRepository;
    private final ProductService productService;

    public UserService(UserRepo userRepo, ProductRepo productRepo, ProductService productService) {
        this.repository = userRepo;
        this.productRepository = productRepo;
        this.productService = productService;
    }

    public User add(User user) {
        return repository.save(user);
    }

    public User find(String name) {
        var all = repository.findAll();
        User found = null;

        for (User user : all) {
            if (user.getUsername().equalsIgnoreCase(name)) {
                found = user;
                break;
            }
        }
        if (found != null) {
            return found;
            //Ska man verkligen kasta denna exception på flera ställen?!
        } else {
            throw new EntityNotFoundException("User with name " + name + "not found!");
        }
    }

    public void delete(String name) {

        var found = find(name);

        if (found != null) {
            repository.deleteById(found.getId());
        } else {
            throw new EntityNotFoundException("User with name " + name + "not found!");
        }
    }

    public Iterable<User> findAll() {
        return repository.findAll();
    }

    public void buyProduct(long productID, long userID) {
        Optional<Product> bought = productRepository.findById(productID);
        Optional<User> user = repository.findById(userID);

        if(bought.isEmpty() || user.isEmpty()){
            throw new EntityNotFoundException();
        }

        Product foundProduct = bought.get();
        User foundUser = user.get();
        foundUser.purchaseProduct(foundProduct);
        productService.addUserToProduct(foundUser, foundProduct);
        repository.save(foundUser);
    }

    public void addForSale(Product product, long userID) {
        Optional<User> user = repository.findById(userID);
        product.setUsed(Used.YES);
        //exceptionhandling behövs här
        User foundUser = user.get();
        productService.add(product);
        productService.addSellerToProduct(foundUser, product);
        foundUser.addItemForSale(product);
        repository.save(foundUser);
    }

    public Set<Product> deliverForSale(long userID) {
        Optional<User> userOptional = repository.findById(userID);

        //exceptionhandling
        User foundUser = userOptional.get();

        return foundUser.deliverForSale();
    }

    public void sellForSale(long userID, long productID) {
        Optional<Product> bought = productRepository.findById(productID);
        Optional<User> user = repository.findById(userID);

        //exceptionhandling behövs här
        Product foundProduct = bought.get();
        User foundUser = user.get();

        foundUser.sellItemForSale(foundProduct);
        productRepository.delete(foundProduct);
    }

    public HttpStatus updateProduct(long userID, long productID, Product updatedItem) {
        Optional<Product> optionalProduct = productRepository.findById(productID);

        //exceptionhandling

        Product originalItem = optionalProduct.get();

        if (originalItem.deliverSeller() == null) {
            throw new CustomException("BÖGEN!!!!!!!!!!!!!!!!!1");
        }

        if (originalItem.deliverSeller().getId() == userID) {
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
        } else {
            return HttpStatus.NOT_ACCEPTABLE;
        }
        //else kasta en exception broder

        updatedItem.setId(originalItem.getId());
        productRepository.save(originalItem);
        return HttpStatus.OK;
    }
}
