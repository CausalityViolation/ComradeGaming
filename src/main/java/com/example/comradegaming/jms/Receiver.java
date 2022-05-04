package com.example.comradegaming.jms;

import com.example.comradegaming.entities.Statistics;
import com.example.comradegaming.service.StatisticsService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    private final StatisticsService statisticsService;

    public Receiver(StatisticsService service) {
        statisticsService = service;
    }

    @JmsListener(destination = Config.MESSAGE_QUEUE)
    public void fetchMessages(@Payload Message message) {

        //funktionalitet
        System.out.println("Fetched stuff:");
        System.out.println(message);

        var price = message.getPrice();
        var category = message.getCategory();
        var timeOfPurchase = message.getLocalDateTime();

        Statistics statisticsToSave = new Statistics(price, category, timeOfPurchase);

        statisticsService.add(statisticsToSave);

    }

}
