package com.example.comradegaming.controller;

import com.example.comradegaming.entities.User;
import com.example.comradegaming.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {

    private final AdminService service;

    public AdminController(AdminService adminService) {
        this.service = adminService;
    }

    @GetMapping("{productID}")
    public ResponseEntity<Iterable<User>> findAllBuyers(@PathVariable long productID) {
        Iterable<User> foundBuyers = service.getBuyers(productID);
        return new ResponseEntity<>(foundBuyers, HttpStatus.OK);
    }

}
