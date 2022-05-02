package com.example.comradegaming.service;

import com.example.comradegaming.entities.Product;
import com.example.comradegaming.entities.User;
import com.example.comradegaming.enums.Used;
import com.example.comradegaming.exceptionHandling.CustomException;
import com.example.comradegaming.repo.ProductRepo;
import com.example.comradegaming.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
        } else {
            throw new CustomException("User with name " + name + " could not be found in database!", HttpStatus.NOT_FOUND);
        }
    }

    public void delete(String name) {

        var found = find(name);

        if (found != null) {
            repository.deleteById(found.getId());
        } else {
            throw new CustomException("User with name " + name + " could not be found in database!", HttpStatus.NOT_FOUND);
        }
    }

    public Iterable<User> findAll() {
        return repository.findAll();
    }

    public void buyProduct(long productID, long userID) {
        Optional<Product> bought = productRepository.findById(productID);
        Optional<User> user = repository.findById(userID);

        if (bought.isEmpty()) {
            throw new CustomException("Product with ID " + productID + " could not be found in database!", HttpStatus.NOT_FOUND);
        }

        if (user.isEmpty()) {
            throw new CustomException("User with ID " + userID + " could not be found in database!", HttpStatus.NOT_FOUND);
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

        if (user.isEmpty()) {
            throw new CustomException("User with ID " + userID + " could not be found in database!", HttpStatus.NOT_FOUND);
        }

        User foundUser = user.get();
        productService.add(product);
        productService.addSellerToProduct(foundUser, product);
        foundUser.addItemForSale(product);
        repository.save(foundUser);
    }

    public Set<Product> deliverForSale(long userID) {
        Optional<User> userOptional = repository.findById(userID);

        if (userOptional.isEmpty()) {
            throw new CustomException("User with ID " + userID + " could not be found in database!", HttpStatus.NOT_FOUND);
        }
        User foundUser = userOptional.get();

        return foundUser.deliverForSale();
    }

    public void sellForSale(long userID, long productID) {
        Optional<Product> bought = productRepository.findById(productID);
        Optional<User> user = repository.findById(userID);

        Product foundProduct = isProductPresent(productID, bought);

        User foundUser = isUserPresent(userID, user);

        foundUser.sellItemForSale(foundProduct);
        productRepository.delete(foundProduct);
    }

    public HttpStatus updateProduct(long userID, long productID, Product updatedItem) {
        Optional<Product> optionalProduct = productRepository.findById(productID);

        if (optionalProduct.isEmpty()) {
            throw new CustomException("Product with ID " + productID + " could not be found in database!", HttpStatus.NOT_FOUND);
        }

        Product originalItem = optionalProduct.get();

        if (originalItem.deliverSeller() == null) {
            throw new CustomException("BÖGEN!!!!!!!!!!!!!!!!!1");
        }

        if (originalItem.deliverSeller().getId() == userID) {
            setData(updatedItem, originalItem);
        } else {
            throw new CustomException("Insufficient Data", HttpStatus.BAD_REQUEST);
        }

        updatedItem.setId(originalItem.getId());
        productRepository.save(originalItem);
        return HttpStatus.OK;
    }

    static void setData(Product updatedItem, Product originalItem) {
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
    }

    private User isUserPresent(long userID, Optional<User> user) {
        if (user.isEmpty()) {
            throw new CustomException("User with ID " + userID + " could not be found in database!", HttpStatus.NOT_FOUND);
        }
        return user.get();
    }

    private Product isProductPresent(long productID, Optional<Product> bought) {
        if (bought.isEmpty()) {
            throw new CustomException("Product with ID " + productID + " could not be found in database!", HttpStatus.NOT_FOUND);
        }
        return bought.get();
    }

    private void doesUserHaveData(User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new CustomException("User has invalid or insufficient data", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    private void isUserNameUnique(User user) {

        var database = repository.findAll();

        for (User userFoundInDatabase : database) {
            if (userFoundInDatabase.getUsername().equalsIgnoreCase(user.getUsername())) {
                throw new CustomException("User with username " + user.getUsername() + " already in database", HttpStatus.CONFLICT);
            }
        }

    }
}
