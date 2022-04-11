package entities;

import enums.Tag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public abstract class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private int price;
    private String productDescription;
    private boolean used = false;

    @ManyToOne
    private User seller;
    @ElementCollection
    ArrayList<Tag> tags = new ArrayList<>();

    @ManyToMany(mappedBy = "owned")
    List<User> buyers;

    //lista ut hur allt ska göras sen
    String imageURL;


    public Product(String name, int price, String productDescription, String imageURL) {
        this.name = name;
        this.price = price;
        this.productDescription = productDescription;
        this.imageURL = imageURL;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
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
