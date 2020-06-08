package com.mobile.lab9_10.models;

import com.mobile.lab9_10.dataAccess.GlobalException;
import com.mobile.lab9_10.dataAccess.NoDataException;
import com.mobile.lab9_10.dataAccess.ServiceCourse;
import com.mobile.lab9_10.entities.Course;

import java.util.Collection;

public class CourseModel {

    private static CourseModel instance = null;
    private ServiceCourse serviceCourse;

    private CourseModel(String contextPath){
        this.serviceCourse = new ServiceCourse(contextPath);
    }

    public static CourseModel getInstance(String contextPath){
        if (instance == null){
            instance = new CourseModel(contextPath);
        }
        return instance;
    }

    public Collection searchCourse(int courseID) throws NoDataException, GlobalException{
        return this.serviceCourse.searchCourse(courseID);
    }

    public Collection listCourses() throws NoDataException, GlobalException{
        return this.serviceCourse.listCourses();
    }

    public void deleteCourse(int courseID) throws NoDataException, GlobalException {
        this.serviceCourse.deleteCourse(courseID);
    }

    public void insertCourse(Course course) throws NoDataException, GlobalException {
        this.serviceCourse.insertCourse(course);
    }

    public void updateCourse(Course course) throws NoDataException, GlobalException {
        this.serviceCourse.updateCourse(course);
    }

    public Collection searchStudentCourses(int studentID)throws NoDataException, GlobalException{
        return this.serviceCourse.searchStudentCourses(studentID);
    }

}
