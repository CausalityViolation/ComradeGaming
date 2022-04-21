package com.example.comradegaming.entities;

import com.example.comradegaming.enums.Category;
import com.example.comradegaming.enums.Company;
import com.example.comradegaming.enums.Garment;
import com.example.comradegaming.enums.Tag;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Clothing extends Product {

    private Company brand;
    private Garment garment;

    public String getBajs() {
        return bajs;
    }

    public void setBajs(String bajs) {
        this.bajs = bajs;
    }

    private String bajs;

    public Clothing() {
    }

    public Clothing(String name, int price, String productDescription, Company brand, Garment garment, String imageUrl, Category category) {
        super(name, price, productDescription, imageUrl, category);
        this.brand = brand;
        this.garment = garment;
    }

    public Company getBrand() {
        return brand;
    }

    public void setBrand(Company brand) {
        this.brand = brand;
    }

    public Garment getGarment() {
        return garment;
    }

    public void setGarment(Garment garment) {
        this.garment = garment;
    }

    /*
    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void addTagSingle(Tag tag) {
        tags.add(tag);
    }

    public void addTagList(List<Tag> tags) {
        this.tags.addAll(tags);
    }

     */

}
