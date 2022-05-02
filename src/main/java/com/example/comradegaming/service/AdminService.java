package com.example.comradegaming.service;

import com.example.comradegaming.entities.Admin;
import com.example.comradegaming.exceptionHandling.CustomException;
import com.example.comradegaming.repo.AdminRepo;
import com.example.comradegaming.repo.ProductRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@Service
public class AdminService {

    private final AdminRepo repository;
    private final ProductRepo productRepo;

    public AdminService(AdminRepo adminRepo, ProductRepo productRepo) {
        this.repository = adminRepo;
        this.productRepo = productRepo;
    }


    public String add(Admin admin) {
        doesAdminHaveData(admin);
        isAdminNameUnique(admin);
        repository.save(admin);
        return "successfully added new Admin";
    }

    public Admin find(String name) {
        var optionalAdmin = repository.findById(name);
        isAdminPresent(optionalAdmin, name);
        return optionalAdmin.get();
    }

    public void delete(String name) {
        var found = repository.findById(name);
        isAdminPresent(found, name);
        repository.deleteById(name);
    }

    public Iterable<Admin> findAll() {
        return repository.findAll();
    }

    private Admin isAdminPresent(Optional<Admin> found, String adminName) {
        if (found.isEmpty()) {
            throw new CustomException("Admin with name " + adminName + " not present in database", HttpStatus.NOT_FOUND);
        }
        return found.get();
    }

    private void doesAdminHaveData(Admin admin) {
        if (admin.getAdminName() == null || admin.getAdminPassword() == null) {
            throw new CustomException("Trying to create Admin with invalid data", HttpStatus.BAD_REQUEST);
        }
    }

    public void isAdminNameUnique(Admin admin) {
        Iterable<Admin> allAdmins = repository.findAll();

        for (Admin adminInDatabase : allAdmins) {
            if (adminInDatabase.getAdminName().equalsIgnoreCase(admin.getAdminName())) {
                throw new CustomException("Admin with username " + admin.getAdminName() + " already exists in database", HttpStatus.NOT_ACCEPTABLE);
            }
        }
    }

    public void updatePassword(String adminName, String newPassword) {
        Optional<Admin> optionalAdmin = repository.findById(adminName);
        Admin adminToUpdate = isAdminPresent(optionalAdmin, adminName);

        //Lägg till så den checkar ditt gamla passord innan du får byta
        adminToUpdate.setAdminPassword(newPassword);
        repository.save(adminToUpdate);
    }
}
