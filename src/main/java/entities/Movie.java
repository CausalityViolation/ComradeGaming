package entities;

import enums.Category;
import enums.Company;
import enums.Tag;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;

@Entity
@Table
public class Movie extends Product {

    private Company publisher;

    public Movie() {
    }

    public Movie(String name, int price, String productDescription, Company publisher, String imageUrl, Category category) {
        super(name, price, productDescription, imageUrl, category);
        this.publisher = publisher;
    }

    public Company getPublisher() {
        return publisher;
    }

    public void setPublisher(Company publisher) {
        this.publisher = publisher;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

}
