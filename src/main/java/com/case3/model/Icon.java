package com.case3.model;

public class Icon {
    private int id;
    private String linkIcon;

    public Icon(int id, String linkIcon) {
        this.id = id;
        this.linkIcon = linkIcon;
    }

    public Icon() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLinkIcon() {
        return linkIcon;
    }

    public void setLinkIcon(String linkIcon) {
        this.linkIcon = linkIcon;
    }
}
