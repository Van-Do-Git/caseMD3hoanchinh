package com.case3.model;

public class Limited {
    private int id;
    private int limitDay;
    private int limitMonth;

    public Limited() {
    }

    public Limited(int id, int limitDay, int limitMonth) {
        this.id = id;
        this.limitDay = limitDay;
        this.limitMonth = limitMonth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLimitDay() {
        return limitDay;
    }

    public void setLimitDay(int limitDay) {
        this.limitDay = limitDay;
    }

    public int getLimitMonth() {
        return limitMonth;
    }

    public void setLimitMonth(int limitMonth) {
        this.limitMonth = limitMonth;
    }
}
