package com.example.comradegaming.entities;

import com.example.comradegaming.enums.Category;
import com.example.comradegaming.enums.Company;
import com.example.comradegaming.enums.Tag;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;

@Entity
public class Movie extends Product {

    private Company publisher;


    public Movie() {
    }

    public Movie(String name, int price, String productDescription, Company publisher, String imageUrl) {
        super(name, price, productDescription, imageUrl, Category.Movie);
        this.publisher = publisher;
    }

    @Override
    public String getInformation() {
        String info = getName() + " is a movie published by " + publisher
                + ". It costs " + getPrice() + " rubels.";
        return info;
    }

    public Company getPublisher() {
        return publisher;
    }

    public void setPublisher(Company publisher) {
        this.publisher = publisher;
    }
/*
    public ArrayList<Tag> getTags() {
        return tags;
    }

 */

}
