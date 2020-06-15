package com.mobile.lab9_10.models;

import com.mobile.lab9_10.dataAccess.GlobalException;
import com.mobile.lab9_10.dataAccess.NoDataException;
import com.mobile.lab9_10.dataAccess.ServiceStudent;
import com.mobile.lab9_10.entities.Student;

import java.util.ArrayList;
import java.util.Collection;

public class StudentModel {

    private static StudentModel instance = null;
    private ServiceStudent serviceStudent;

    private StudentModel(String contextPath){
        this.serviceStudent = new ServiceStudent(contextPath);
    }

    public static StudentModel getInstance(String contextPath){
        if(instance == null){
            instance = new StudentModel(contextPath);
        }
        return instance;
    }

    public Collection searchStudent(int studentID) throws NoDataException, GlobalException {
        return this.serviceStudent.searchStudent(studentID);
    }

    public Collection listStudents() throws NoDataException, GlobalException{
        return this.serviceStudent.listStudents();
    }

    public void deleteStudent(int studentID) throws NoDataException, GlobalException {
        this.serviceStudent.deleteStudent(studentID);
    }

    public void insertStudent(Student student) throws NoDataException, GlobalException {
        this.serviceStudent.insertStudent(student);
    }

    public void updateStudent(Student student) throws NoDataException, GlobalException {
        this.serviceStudent.updateStudent(student);
    }
    public void insertEnroll(ArrayList<Integer> courses, int studentId) throws NoDataException, GlobalException {
        this.serviceStudent.insertEnroll(courses,studentId);
    }

}
