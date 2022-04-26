package com.example.comradegaming.entities;

import javax.persistence.*;

@Entity
@Table
public class Admin {

    @Id
    @GeneratedValue
    private long id;
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
