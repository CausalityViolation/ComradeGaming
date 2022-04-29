package com.example.comradegaming.entities;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class User {
    @Id
    @GeneratedValue
    private long id;
    //username borde vara unikt...
    private String username;
    private String password;

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Set<Product> owned;

    @OneToMany(mappedBy = "seller")
    private Set<Product> forSale;

    //Rekomenderade produkter. Vilka produkter har flest matchange tags med användaren???

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void purchaseProduct(Product product) {
        owned.add(product);
    }

    public void removePurchasedProduct(Product product) {
        owned.remove(product);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Product> getOwned() {
        return owned;
    }

    public void addItemForSale(Product item) {
        forSale.add(item);
    }

//    public Set<Product> getForSale() {
//        return forSale;
//    }

    public long getId() {
        return id;
    }

    public Set<Product> deliverForSale(){
        return forSale;
    }

    public void sellItemForSale(Product foundProduct) {
        forSale.removeIf(product -> product.getId() == foundProduct.getId());
    }
}
