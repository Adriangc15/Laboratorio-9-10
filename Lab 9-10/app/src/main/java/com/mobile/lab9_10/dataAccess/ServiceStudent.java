package com.mobile.lab9_10.dataAccess;

import android.database.Cursor;
import android.database.SQLException;
import com.mobile.lab9_10.db.DataBase;
import com.mobile.lab9_10.entities.Student;
import java.util.ArrayList;
import java.util.Collection;

public class ServiceStudent extends Service {

    public  ServiceStudent(String contextPath){
        super(contextPath);
    }

    public Collection searchStudent(int studentID) throws NoDataException, GlobalException {
        try {
            this.connect();
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no está disponible.");
        }

        ArrayList collection  = new ArrayList();
        Student student = null;
        String[] arg = {String.valueOf(studentID)};

        try {
            Cursor cursor = this.connection.rawQuery(DataBase.SEARCH_STUDENT , arg);
            while (cursor.moveToNext()){
                student = new Student(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3));

                collection.add(student);
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

    public Collection listStudents() throws NoDataException, GlobalException{
        try {
            this.connect();
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no está disponible.");
        }

        ArrayList collection = new ArrayList();
        Student student = null;

        try {
            Cursor cursor = this.connection.rawQuery(DataBase.LIST_STUDENTS, null);
            while (cursor.moveToNext()){
                student = new Student(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3));

                collection.add(student);
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

    public void deleteStudent(int studentID) throws NoDataException, GlobalException {
        try {
            this.connect();
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no está disponible.");
        }

        String[] arg = {String.valueOf(studentID),
                        String.valueOf(studentID)};

        try {
            this.connection.beginTransaction();
            this.connection.execSQL(DataBase.DELETE_STUDENT , arg);
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

    public void insertStudent(Student student) throws NoDataException, GlobalException {
        try {
            this.connect();
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no está disponible.");
        }

        String[] arg = {
                String.valueOf(student.getId()),
                student.getName(),
                student.getLastName(),
                String.valueOf(student.getAge())};

        try {
            this.connection.beginTransaction();
            this.connection.execSQL(DataBase.INSERT_STUDENT , arg);
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

    public void updateStudent(Student student) throws NoDataException, GlobalException {
        try {
            this.connect();
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no está disponible.");
        }

        String[] arg = {
                student.getName(),
                student.getLastName(),
                String.valueOf(student.getAge()),
                String.valueOf(student.getId())};

        try {
            this.connection.beginTransaction();
            this.connection.execSQL(DataBase.UPDATE_STUDENT , arg);
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

}
