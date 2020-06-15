package com.mobile.lab9_10.ui.student;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.CollationKey;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.mobile.lab9_10.R;
import com.mobile.lab9_10.dataAccess.GlobalException;
import com.mobile.lab9_10.dataAccess.NoDataException;
import com.mobile.lab9_10.entities.Course;
import com.mobile.lab9_10.entities.Student;
import com.mobile.lab9_10.models.CourseModel;
import com.mobile.lab9_10.models.StudentModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class StudentEnroll extends AppCompatActivity {

    private CourseModel courseModel;
    private StudentModel studentModel;

    private Spinner spinnerFreeCourses;
    private Spinner spinnerEnrollCourses;
    private Button saveCoursesEnroll;

    private ArrayAdapter<Course> adapterFreeCourses;
    private ArrayAdapter<Course> adapterEnrollCourses;

    private Boolean isFirstTime1 = true;
    private Boolean isFirstTime2 = true;

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
        this.saveCoursesEnroll = (Button) findViewById(R.id.saveCoursesEnrollBrn);

        this.loadCourses();
        spinnerFreeCourses.setSelection(0);
        spinnerEnrollCourses.setSelection(0);

        this.saveCoursesEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> courses = new ArrayList<>();
                for (int i = 0; i < adapterEnrollCourses.getCount(); i ++){
                    Course aux = (Course) adapterEnrollCourses.getItem(i);
                    courses.add(aux.getId());
                }
                try {
                    studentModel.insertEnroll(courses, student.getId());
                } catch (NoDataException e) {
                    e.printStackTrace();
                } catch (GlobalException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void onResume() {
        super.onResume();
        this.spinnerFreeCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirstTime1){
                    isFirstTime1 = false;
                    return;
                }
                Course course = (Course) spinnerFreeCourses.getItemAtPosition(position);
                adapterEnrollCourses.add(course);
                adapterFreeCourses.remove(course);
                spinnerFreeCourses.setSelection(0);

                saveCoursesEnroll.setEnabled(true);

                isFirstTime1 = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        this.spinnerEnrollCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirstTime2){
                    isFirstTime2 = false;
                    return;
                }
                Course course = (Course) spinnerEnrollCourses.getItemAtPosition(position);
                adapterFreeCourses.add(course);
                adapterEnrollCourses.remove(course);
                spinnerEnrollCourses.setSelection(0);

                if(adapterEnrollCourses.getCount() == 1){
                    saveCoursesEnroll.setEnabled(false);
                }

                isFirstTime2 = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
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

            coursesList.add(0, new Course());

            this.adapterFreeCourses = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, coursesList);
            this.adapterFreeCourses.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            this.spinnerFreeCourses.setAdapter(this.adapterFreeCourses);

            this.adapterEnrollCourses = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, new ArrayList<Course>(Arrays.asList(new Course())));
            this.adapterEnrollCourses.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            this.spinnerEnrollCourses.setAdapter(this.adapterEnrollCourses);

        } catch (NoDataException e) {
            e.printStackTrace();
        } catch (GlobalException e) {
            e.printStackTrace();
        }
    }
}