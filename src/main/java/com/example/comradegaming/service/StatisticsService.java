package com.example.comradegaming.service;

import com.example.comradegaming.entities.Statistics;
import com.example.comradegaming.repo.StatisticsRepo;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {
    private final StatisticsRepo repository;

    public StatisticsService(StatisticsRepo repository){
    this.repository = repository;
    }

    public Iterable<Statistics> findAll(){
        return repository.findAll();
    }

    public void add(Statistics statistics){
        repository.save(statistics);
    }
}
