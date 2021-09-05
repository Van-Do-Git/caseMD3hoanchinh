package com.case3.model;

public class Category {
    private int id;
    private String name;
    private String linkIcon;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(int id, String name, String linkIcon) {
        this.id = id;
        this.name = name;
        this.linkIcon = linkIcon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkIcon() {
        return linkIcon;
    }

    public void setLinkIcon(String linkIcon) {
        this.linkIcon = linkIcon;
    }
}
