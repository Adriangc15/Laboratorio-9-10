package com.mobile.lab9_10.entities;

public class Course {

    private int id;
    private String description;
    private  String credits;

    public Course(int id, String description, String credits) {
        this.id = id;
        this.description = description;
        this.credits = credits;
    }

    public Course() {
        this.id = 0;
        this.description = "";
        this.credits = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }
}
