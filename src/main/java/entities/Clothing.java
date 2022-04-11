package entities;

import enums.Company;
import enums.Garment;
import enums.Tag;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Clothing extends Product {

    private Company brand;
    private Garment garment;

    public Clothing() {
    }

    public Clothing(String name, int price, String productDescription, Company brand, Garment garment, String imageUrl) {
        super(name, price, productDescription, imageUrl);
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

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void addTagSingle(Tag tag) {
        tags.add(tag);
    }

    public void addTagList(List<Tag> tags) {
        this.tags.addAll(tags);
    }

}
