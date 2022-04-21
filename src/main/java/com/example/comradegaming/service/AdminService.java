package com.example.comradegaming.service;

import com.example.comradegaming.repo.AdminRepo;
import com.example.comradegaming.entities.Admin;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepo repository;

    public AdminService(AdminRepo adminRepo) {
        this.repository = adminRepo;
    }

    public String add(Admin admin) {
        repository.save(admin);
        return "successfully added new Admin";
    }

    public Optional<Admin> find(String name) {
        var found = repository.findById(name);
        if (found.isPresent()) {
            return found;
        } else {
            throw new EntityNotFoundException("Admin with name " + name + "not found!");
        }
    }

    public void delete(String name) {

        var found = repository.findById(name);
        if (found.isPresent()) {
            repository.deleteById(name);
        } else {
            throw new EntityNotFoundException("Admin with name " + name + "not found!");
        }
    }

    public Iterable<Admin> findAll() {
        return repository.findAll();
    }

}
