package com.example.comradegaming.controller;

import com.example.comradegaming.entities.Admin;
import com.example.comradegaming.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class AdminController {

    private final AdminService service;

    public AdminController(AdminService adminService) {
        this.service = adminService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Admin>> findAllAdmins() {
        Iterable<Admin> foundAdmins = service.findAll();
        return new ResponseEntity<>(foundAdmins, HttpStatus.OK);
    }

    @GetMapping("{name}")
    public ResponseEntity<Admin> getByName(@PathVariable String name) {
        Admin foundAdmin = service.find(name);
        return new ResponseEntity<>(foundAdmin, HttpStatus.OK);
    }

    @PatchMapping("update/{adminName}/{newPassword}")
    public ResponseEntity<Admin> updateAdminPassword(@PathVariable String adminName, @PathVariable String newPassword) {
        Admin foundAdmin = service.find(adminName);
        service.updatePassword(adminName, newPassword);
        return new ResponseEntity<>(foundAdmin, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        service.add(admin);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
