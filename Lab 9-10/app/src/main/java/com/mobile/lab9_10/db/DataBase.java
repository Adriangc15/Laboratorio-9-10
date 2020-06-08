package com.mobile.lab9_10.db;
import android.database.SQLException;
import android.database.sqlite.*;
import android.util.Log;

public class DataBase {

    private static final String CREATE_TBL_COURSES = "create table Courses ( " +
            "id integer PRIMARY KEY not null," +
            "description text," +
            "credits text );";

    private static final String CREATE_TBL_STUDENTS = "create table Students ( " +
            "id integer PRIMARY KEY autoincrement, " +
            "name text, " +
            "lastName, " +
            "age integer);";

    private static final String CREATE_TBL_COU_STU = "create table StudentCourses (" +
            "studentId integer not null," +
            "courseId integer not null," +
            "FOREIGN KEY (studentId) REFERENCES Students(id)," +
            "FOREIGN KEY (courseId) REFERENCES Courses(id));";

    private static final String [] CREATE_DATA = {
            "Insert into Courses(id, description, credits) values (1, 'Mobiles', '4')",
            "Insert into Courses(id, description, credits) values (2, 'Algebra', '5')",
            "Insert into Courses(id, description, credits) values (3, 'Inglés', '3')",

            "Insert into Students(id, name, lastName, age) values (1, 'Adrian', 'Chavarria', 23)",
            "Insert into Students(id, name, lastName, age) values (2, 'Antonio', 'Quesada', 22)",
            "Insert into Students(id, name, lastName, age) values (3, 'Juan', 'Murillo', 40)",

            "Insert into StudentCourses(studentId, courseId) values (1, 2)",
            "Insert into StudentCourses(studentId, courseId) values (2, 1)",
            "Insert into StudentCourses(studentId, courseId) values (3, 3)",
            "Insert into StudentCourses(studentId, courseId) values (1, 1)"
    };

    // Begging of Queries for Courses --------------------------------------------------------------
    public static final String SEARCH_COURSE = "Select " +
            "id, " +
            "description, " +
            "credits " +
            "From Courses " +
            "Where id = ?";

    public static final String SEARCH_STUDENT_COURSES = "Select " +
            "id, " +
            "description, " +
            "credits " +
            "From Courses " +
            "Inner Join StudentCourses On StudentCourses.courseId = Courses.id " +
            "Where StudentCourses.studentId = ?";

    public static final String LIST_COURSE = "Select " +
            "id, " +
            "description, " +
            "credits " +
            "From Courses";

    public static final String DELETE_COURSE = "Delete From " +
            "Courses " +
            "Where id = ?";

    public static final String INSERT_COURSE = "Insert into Courses(id, description, credits) values (?, ?, ?)";

    public static final String UPDATE_COURSE = "Update Courses Set description = ? credits = ? Where id = ?";

    // End of Queries for Courses ------------------------------------------------------------------

    // Begging of Queries for Students -------------------------------------------------------------
    public static final String SEARCH_STUDENT = "Select " +
            "id, " +
            "name, " +
            "lastName, " +
            "age " +
            "From Students " +
            "Where id = ?";

    public static final String LIST_STUDENTS = "Select " +
            "id, " +
            "name, " +
            "lastName, " +
            "age " +
            "From Students";

    public static final String DELETE_STUDENT = "Delete From " +
            "Students " +
            "Where id = ?";

    public static final String INSERT_STUDENT = "Insert into Students(id, name, lastName, age) values (?, ?, ?, ?)";

    public static final String UPDATE_STUDENT = "Update Students Set name = ? lastName = ? age = ? Where id = ?";
    // End of Queries for Students -----------------------------------------------------------------

    private static final String DB_PATH = "%s/lab9-10";

    private String contextPath;

    public DataBase(String contextPath, boolean initDB){
        if (initDB){
            this.contextPath = contextPath;
            this.initDB();
        }
    }
    public DataBase(String contextPath){
        this.contextPath = contextPath;
    }

    public void initDB(){
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(String.format(DB_PATH, this.contextPath), null, SQLiteDatabase.CREATE_IF_NECESSARY);
            if (db.isOpen()){
//                db.execSQL(CREATE_TBL_COURSES);
//                db.execSQL(CREATE_TBL_STUDENTS);
//                db.execSQL(CREATE_TBL_COU_STU);
//
//                for (String query: CREATE_DATA) {
//                    db.execSQL(query);
//                }

            }
        } catch (SQLException e) {
            Log.e("SQLException", e.toString());
        } finally {
            if (db != null){
                db.close();
            }
        }
    }

}
