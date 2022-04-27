package com.example.comradegaming.entities;

import com.example.comradegaming.enums.Category;
import com.example.comradegaming.enums.Tag;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public abstract class Product implements ProductInterface {
    //Om allt kraschar sen kanske det är pga att denna klass är abstrakt. Låter det vara så länge

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private int price;
    private String productDescription;
    //Kommer inte ihåg vad denna var till för?
    private boolean used = false;
    private Category category;
    //lista ut hur allt ska göras sen
    String imageURL;
    @JsonBackReference
    @ManyToOne
    private User seller;
    /*
    @ElementCollection
    ArrayList<Tag> tags = new ArrayList<>();

     */
    //FÖR ATT REKOMENDERA KÖP. MATCHA KÖPTA SPEL OSV.
    @ManyToMany(mappedBy = "owned")
    List<User> buyers;


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
        return "こんな風にアップリ使用出来ません。他の商品選択して下さい。CHOSEN ITEM IS CLASS PRODUCT. YOU ARE USING THE APPLICATION IN AN INCORRECT WAY YOU FOOL.";
    }

    public int getId() {
        return id;
    }

    //För testning. Ska tas bort när applikationen är klar.
    public void setId(int id) {
        this.id = id;
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

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
}
