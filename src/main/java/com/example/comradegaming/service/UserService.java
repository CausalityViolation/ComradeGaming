package com.example.comradegaming.service;

import com.example.comradegaming.entities.Product;
import com.example.comradegaming.entities.User;
import com.example.comradegaming.enums.Used;
import com.example.comradegaming.exceptionHandling.CustomException;
import com.example.comradegaming.repo.ProductRepo;
import com.example.comradegaming.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo repository;
    private final ProductRepo productRepository;
    private final ProductService productService;
    private final BCryptPasswordEncoder pwEncoder;

    public UserService(UserRepo userRepo, ProductRepo productRepo, ProductService productService, BCryptPasswordEncoder pwEncoder) {
        this.repository = userRepo;
        this.productRepository = productRepo;
        this.productService = productService;
        this.pwEncoder = pwEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new CustomException("User with name " + username + " could not be found in database!", HttpStatus.NOT_FOUND);
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>(1);
        authorities.add(new SimpleGrantedAuthority(user.deliverRole()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.deliverPassword(), authorities);
    }

    public User add(User user) {
        doesUserHaveData(user);
        isUserNameUnique(user);
        encryptPassword(user);
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

    //Uppdaterat med Bcrypt
    private void encryptPassword(User user) {
        user.setPassword(pwEncoder.encode(user.deliverPassword()));
    }

    //Uppdaterat med Bcrypt
    public void updatePassword(long ID, String password) {
        Optional<User> optionalUser = repository.findById(ID);
        User user = isUserPresent(ID, optionalUser);
        user.setPassword(password);
        encryptPassword(user);
        repository.save(user);
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
        product.setUsed(Used.Yes);

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
            throw new CustomException("Can't find what you're looking for!", HttpStatus.NOT_FOUND);
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
        if (user.getUsername() == null || user.deliverPassword() == null) {
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

    public User findById(long id) {
        Optional<User> optionalUser = repository.findById(id);
        return isUserPresent(id, optionalUser);
    }

    public void makeAdmin(User user) {
        user.makeAdmin();
        repository.save(user);
    }


}
