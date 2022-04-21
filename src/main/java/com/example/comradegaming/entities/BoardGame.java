package com.example.comradegaming.entities;

import com.example.comradegaming.enums.Category;
import com.example.comradegaming.enums.Company;
import com.example.comradegaming.enums.Tag;

import javax.persistence.*;
import java.util.List;

@Entity
public class BoardGame extends Product {
    private Company publisher;


    public BoardGame() {
    }

    public BoardGame(String name, int price, String productDescription, Company publisher, String imageUrl, Category category) {
        super(name, price, productDescription, imageUrl, category);
        this.publisher = publisher;
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
