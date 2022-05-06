package com.example.comradegaming.entities;

import com.example.comradegaming.enums.Category;
import com.example.comradegaming.enums.Used;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Product implements ProductInterface {
    //tog bort abstract för testning. Bör dock sättas som abstract igen...

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private int price;
    private String productDescription;
    private Category category;
    private Used used;
    //lista ut hur allt ska göras sen
    String imageURL;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private User seller;
    /*
    @ElementCollection
    ArrayList<Tag> tags = new ArrayList<>();

     */
    //FÖR ATT REKOMENDERA KÖP. MATCHA KÖPTA SPEL OSV.
    @ManyToMany(mappedBy = "owned")
    Set<User> buyers;


    public Product(String name, int price, String productDescription, String imageURL, Category category) {
        this.name = name;
        this.price = price;
        this.productDescription = productDescription;
        this.imageURL = imageURL;
        this.category = category;
    }

    public Product() {
    }

    @Override
    public String getInformation() {
        return "Product information has not yet been entered.";
    }

    public Used getUsed() {
        return used;
    }

    public void setUsed(Used used) {
        this.used = used;
    }

    public void addUserToBuyerList(User user) {
        buyers.add(user);
    }

    public long getId() {
        return id;
    }

    //För testning. Ska tas bort när applikationen är klar.
    public void setId(long id) {
        this.id = id;
    }

    public User deliverSeller() {
        return seller;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Set<User> getBuyers() {
        return buyers;
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

//    public User getSeller() {
//        return seller;
//    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

}
