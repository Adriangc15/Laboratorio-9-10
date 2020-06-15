package com.mobile.lab9_10.dataAccess;

import android.database.Cursor;
import android.database.SQLException;

import com.mobile.lab9_10.db.DataBase;
import com.mobile.lab9_10.entities.Course;
import java.util.ArrayList;
import java.util.Collection;

public class ServiceCourse extends Service {

    public ServiceCourse(String contextPath){
        super(contextPath);
    }

    public Collection searchCourse(int courseID) throws NoDataException, GlobalException {
        try {
            this.connect();
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no está disponible.");
        }

        ArrayList collection  = new ArrayList();
        Course course = null;
        String[] arg = {String.valueOf(courseID)};

        try {
            Cursor cursor = this.connection.rawQuery(DataBase.SEARCH_COURSE , arg);
            while (cursor.moveToNext()){
                course = new Course(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2));

                collection.add(course);
            }
        }catch (SQLException e){
            throw new GlobalException("Sentencia no  válida.");
        } finally {
            try {
                if (this.connection != null && this.connection.isOpen()){
                    this.disconnect();
                }
            } catch (SQLException e){
                throw new GlobalException("Estatutos nulos o inválidos");
            }
        }
        return collection;
    }

    public Collection listCourses() throws NoDataException, GlobalException{
        try {
            this.connect();
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no está disponible.");
        }

        ArrayList collection = new ArrayList();
        Course course = null;

        try {
            Cursor cursor = this.connection.rawQuery(DataBase.LIST_COURSE, null);
            while (cursor.moveToNext()){
                course = new Course(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2));

                collection.add(course);
            }
        }catch (SQLException e){
            throw new GlobalException("Sentencia no  válida.");
        } finally {
            try {
                if (this.connection != null && this.connection.isOpen()){
                    this.disconnect();
                }
            } catch (SQLException e){
                throw new GlobalException("Estatutos nulos o inválidos");
            }
        }
        return collection;
    }

    public void deleteCourse(int courseID) throws NoDataException, GlobalException {
        try {
            this.connect();
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no está disponible.");
        }

        String[] arg = {String.valueOf(courseID),
                        String.valueOf(courseID)};

        try {
            this.connection.beginTransaction();
            this.connection.execSQL(DataBase.DELETE_COURSE , arg);
            this.connection.setTransactionSuccessful();
        }catch (SQLException e){
            throw new GlobalException("Sentencia no  válida.");
        } finally {
            try {
                if (this.connection != null && this.connection.isOpen()){
                    this.connection.endTransaction();
                    this.disconnect();
                }
            } catch (SQLException e){
                throw new GlobalException("Estatutos nulos o inválidos");
            }
        }
    }

    public void insertCourse(Course course) throws NoDataException, GlobalException {
        try {
            this.connect();
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no está disponible.");
        }

        String[] arg = {
                String.valueOf(course.getId()),
                course.getDescription(),
                course.getCredits()};

        try {
            this.connection.beginTransaction();
            this.connection.execSQL(DataBase.INSERT_COURSE , arg);
            this.connection.setTransactionSuccessful();
        }catch (SQLException e){
            throw new GlobalException("Sentencia no  válida.");
        } finally {
            try {
                if (this.connection != null && this.connection.isOpen()){
                    this.connection.endTransaction();
                    this.disconnect();
                }
            } catch (SQLException e){
                throw new GlobalException("Estatutos nulos o inválidos");
            }
        }
    }

    public void updateCourse(Course course) throws NoDataException, GlobalException {
        try {
            this.connect();
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no está disponible.");
        }

        String[] arg = {
                course.getDescription(),
                course.getCredits(),
                String.valueOf(course.getId())};

        try {
            this.connection.beginTransaction();
            this.connection.execSQL(DataBase.UPDATE_COURSE , arg);
            this.connection.setTransactionSuccessful();
        }catch (SQLException e){
            throw new GlobalException("Sentencia no  válida.");
        } finally {
            try {
                if (this.connection != null && this.connection.isOpen()){
                    this.connection.endTransaction();
                    this.disconnect();
                }
            } catch (SQLException e){
                throw new GlobalException("Estatutos nulos o inválidos");
            }
        }
    }

    public Collection searchStudentCourses(int studentID)throws NoDataException, GlobalException{
        try {
            this.connect();
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no está disponible.");
        }

        String[] arg = { String.valueOf(studentID) };
        ArrayList collection = new ArrayList();
        Course course = null;

        try {
            Cursor cursor = this.connection.rawQuery(DataBase.SEARCH_STUDENT_COURSES, arg);
            while (cursor.moveToNext()){
                course = new Course(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2));

                collection.add(course);
            }
        }catch (SQLException e){
            throw new GlobalException("Sentencia no  válida.");
        } finally {
            try {
                if (this.connection != null && this.connection.isOpen()){
                    this.disconnect();
                }
            } catch (SQLException e){
                throw new GlobalException("Estatutos nulos o inválidos");
            }
        }
        return collection;
    }

}
