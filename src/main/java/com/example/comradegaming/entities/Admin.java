package com.example.comradegaming.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Admin {

    @Id
    private String adminName;
    private String adminPassword;

    public Admin() {
    }

    public Admin(String userName, String passWord) {
        this.adminName = userName;
        this.adminPassword = passWord;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String userName) {
        this.adminName = userName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String passWord) {
        this.adminPassword = passWord;
    }
}
