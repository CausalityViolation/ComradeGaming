package com.example.comradegaming.jms;

import com.example.comradegaming.entities.Product;
import com.example.comradegaming.enums.Category;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Sender {

    private final JmsTemplate jmsTemplate;

    public Sender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendInformation(Product purchased) {

        var categoryInfo = purchased.getCategory();
        var priceInfo = purchased.getPrice();

        System.out.println("Sending information..");

        Message message = new Message(LocalDateTime.now(), categoryInfo, priceInfo);
        jmsTemplate.convertAndSend(Config.MESSAGE_QUEUE, message);

        System.out.println("Information sent!");
    }
}
