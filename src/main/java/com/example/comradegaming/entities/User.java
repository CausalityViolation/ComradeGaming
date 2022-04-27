package com.example.comradegaming.entities;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String password;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Product> owned;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.PERSIST)
    private List<Product> forSale;

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

    public List<Product> getOwned() {
        return owned;
    }

    public void addItemForSale(Product item) {
        forSale.add(item);
    }

    public List<Product> getForSale() {
        return forSale;
    }

    public long getId() {
        return id;
    }
}
