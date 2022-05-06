package com.example.comradegaming.entities;

import com.example.comradegaming.enums.Category;
import com.example.comradegaming.enums.Company;
import com.example.comradegaming.enums.Tag;

import javax.persistence.*;

import com.example.comradegaming.enums.Platform;

import java.util.*;

@Entity
public class DigitalGame extends Product {

    private Platform platform;
    private Company developer;
    private Company publisher;

    public DigitalGame() {
    }

    public DigitalGame(Platform platform, Company developer, Company publisher
            , String name, int price, String productDescription, String imageUrl, Category category) {
        super(name, price, productDescription, imageUrl, category);
        this.platform = platform;
        this.developer = developer;
        this.publisher = publisher;
    }

    /*
    private int price;
    private String productDescription;
    private boolean used = false;
    private Category category;
    private Platform platform;
    private Company publisher;
     */

    @Override
    public String getInformation() {

        return getName() + " is a game developed by " + developer + " and published by " + publisher
                + ". It is playable on " + platform + ". It costs " + getPrice() + " rubels.";
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Company getDeveloper() {
        return developer;
    }

    public void setDeveloper(Company developer) {
        this.developer = developer;
    }

    public Company getPublisher() {
        return publisher;
    }

    public void setPublisher(Company publisher) {
        this.publisher = publisher;
    }

    /*
    public void addTagSingle(Tag tag) {
        tags.add(tag);
    }

    public void addTagList(List<Tag> tags) {
        this.tags.addAll(tags);
    }

     */
}

