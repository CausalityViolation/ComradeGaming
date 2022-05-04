package com.example.comradegaming.controller;

import com.example.comradegaming.entities.Statistics;
import com.example.comradegaming.entities.User;
import com.example.comradegaming.service.StatisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stats")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService service) {
        this.statisticsService = service;
    }

    @GetMapping()
    public ResponseEntity<Iterable<Statistics>> findAll() {
        Iterable<Statistics> found = statisticsService.findAll();
        return new ResponseEntity<>(found, HttpStatus.OK);
    }
}
