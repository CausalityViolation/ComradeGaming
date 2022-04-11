package entities;

import enums.Company;
import enums.Tag;

import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
public class Movie extends Product {

    private Company publisher;

    public Movie() {
    }

    public Movie(String name, int price, String productDescription, Company publisher, String imageUrl) {
        super(name, price, productDescription, imageUrl);
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
