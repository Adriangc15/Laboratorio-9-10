package com.mobile.lab9_10.ui.student;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.CollationKey;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mobile.lab9_10.R;
import com.mobile.lab9_10.dataAccess.GlobalException;
import com.mobile.lab9_10.dataAccess.NoDataException;
import com.mobile.lab9_10.entities.Course;
import com.mobile.lab9_10.entities.Student;
import com.mobile.lab9_10.models.CourseModel;
import com.mobile.lab9_10.models.StudentModel;

import java.util.ArrayList;
import java.util.Iterator;

public class StudentEnroll extends AppCompatActivity {

    private CourseModel courseModel;
    private StudentModel studentModel;

    private Spinner spinnerFreeCourses;
    private Spinner spinnerEnrollCourses;

    private ArrayAdapter<Course> adapterFreeCourses;
    private ArrayAdapter<Course> adapterEnrollCourses;

    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_enroll);

        Bundle extras = getIntent().getExtras();
        this.student = (Student) extras.getSerializable(StudentCreate.STUDENT_FLAG);

        this.courseModel = CourseModel.getInstance(getFilesDir().getPath());
        this.studentModel = StudentModel.getInstance(getFilesDir().getPath());

        this.spinnerFreeCourses = (Spinner) findViewById(R.id.spinnerFreeCourses);
        this.spinnerEnrollCourses = (Spinner) findViewById(R.id.spinnerSelectedCourses);

//        this.spinnerFreeCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Course course = (Course) spinnerFreeCourses.getItemAtPosition(position);
//                adapterEnrollCourses.add(course);
//                adapterFreeCourses.remove(course);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                return;
//            }
//        });
//
//        this.spinnerEnrollCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Course course = (Course) spinnerEnrollCourses.getItemAtPosition(position);
//                adapterFreeCourses.add(course);
//                adapterEnrollCourses.remove(course);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        this.spinnerFreeCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Course course = (Course) spinnerFreeCourses.getSelectedItem();
               adapterEnrollCourses.add(course);
               adapterFreeCourses.remove(course);
            }
        });
        this.loadCourses();
    }

    private void loadCourses(){
        try {
            ArrayList<Course> enrolledCourses = (ArrayList<Course>) this.courseModel.searchStudentCourses(this.student.getId());
            ArrayList<Course> coursesList = (ArrayList<Course>) this.courseModel.listCourses();

            Iterator<Course> iterator = coursesList.iterator();
            while (iterator.hasNext()){
                Course aux2 = iterator.next();
                for (Course aux : enrolledCourses){
                    if (aux2.getId() == aux.getId()){
                        iterator.remove();
                    }
                }
            }

            this.adapterFreeCourses = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, coursesList);
            this.adapterFreeCourses.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            this.spinnerFreeCourses.setAdapter(this.adapterFreeCourses);

            this.adapterEnrollCourses = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, new ArrayList<Course>());
            this.adapterEnrollCourses.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            this.spinnerEnrollCourses.setAdapter(this.adapterEnrollCourses);

        } catch (NoDataException e) {
            e.printStackTrace();
        } catch (GlobalException e) {
            e.printStackTrace();
        }
    }
}