package com.case3.model;


import java.util.List;

public class User {
    private int id;
    private String fullName;
    private String phone;
    private String username;
    private String password;
    private String role;
    private boolean status;
    private Limited limited;

    public Limited getLimited() {
        return limited;
    }

    public void setLimited(Limited limited) {
        this.limited = limited;
    }

    public User() {
    }

    public User(int id, String fullName, String phone, String username, String password, String role, boolean status) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public User(String fullName, String phone, String username, String password, String role, boolean status) {
        this.fullName = fullName;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public User(int id, String fullName, String phone, String username, String password, String role, boolean status,Limited limited) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
