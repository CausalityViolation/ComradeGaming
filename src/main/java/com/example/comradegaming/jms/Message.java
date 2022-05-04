package com.example.comradegaming.jms;

import com.example.comradegaming.enums.Category;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Message {

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm:ss")
    private LocalDateTime localDateTime;
    private Category category;
    private long price;

    public Message() {
    }

    public Message(LocalDateTime localDateTime, Category category, long price) {
        this.localDateTime = localDateTime;
        this.category = category;
        this.price = price;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Category getCategory() {
        return category;
    }

    public long getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return
                "Time of purchase: " + localDateTime +
                        "\nCategory: " + category +
                        "\nPrice: " + price + " Rubel";
    }
}
