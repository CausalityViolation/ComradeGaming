package com.example.comradegaming.entities;

import com.example.comradegaming.enums.Category;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long price;
    private Category category;
    private LocalDateTime timestamp;

    public Statistics() {
    }

    public Statistics(long price, Category category, LocalDateTime timestamp) {
        this.price = price;
        this.category = category;
        this.timestamp = timestamp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
