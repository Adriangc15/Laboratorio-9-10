package com.mobile.lab9_10.entities;

import java.util.ArrayList;
import java.util.Collection;

public class Student {

    private int id;
    private String name;
    private String lastName;
    private int age;
    private Collection<Course> courses;

    public Student(int id, String name, String lastName, int age, Collection<Course> courses) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.courses = courses;
    }

    public Student(){
        this.id = 0;
        this.name = "";
        this.lastName = "";
        this.age = 0;
        this.courses = new ArrayList<>();
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Collection<Course> getCourses() {
        return courses;
    }

    public void setCourses(Collection<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course){
        this.courses.add(course);
    }
}
