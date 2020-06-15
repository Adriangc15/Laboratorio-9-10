package com.mobile.lab9_10.ui.course;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.lab9_10.R;
import com.mobile.lab9_10.dataAccess.GlobalException;
import com.mobile.lab9_10.dataAccess.NoDataException;
import com.mobile.lab9_10.entities.Course;
import com.mobile.lab9_10.models.CourseModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CourseCreate extends AppCompatActivity {

    private EditText id;
    private EditText description;
    private EditText credits;
    private ImageButton save;
    private CourseModel courseModel;

    public static final String EDIT_COURSE = "editCourse";
    public static final String ADD_COURSE = "addCourse";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_curso);

        this.courseModel = CourseModel.getInstance(getApplicationContext().toString());

        this.id = (EditText) findViewById(R.id.inputCourseId);
        this.description = (EditText) findViewById(R.id.inputCourseDescription);
        this.credits = (EditText) findViewById(R.id.inputCourseCredits);
        this.save = (ImageButton) findViewById(R.id.saveCourse);
        final TextView title = (TextView) findViewById(R.id.TitleCourse);


        this.id.setText("");
        this.description.setText("");
        this.credits.setText("");

        Bundle extras = getIntent().getExtras();
        final Boolean isEditable = extras.getBoolean(CourseFragment.EDITABLE_FLAG);
        if (isEditable){
            Course course = (Course) extras.getSerializable(CourseFragment.COURSE_FLAG);
            if (course != null){
                title.setText("Editando el curso: " + course.getDescription());
                this.id.setEnabled(false);
                this.id.setText(String.valueOf(course.getId()));
                this.description.setText(course.getDescription());
                this.credits.setText(course.getCredits());
            }
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sId = id.getText().toString();
                String sDescription = description.getText().toString();
                String sCredits = credits.getText().toString();
                if(isValidForm(sId,sDescription, sCredits)){
                    Course course = new Course(Integer.parseInt(sId), sDescription, sCredits);

                    if (isEditable) {

                        try {
                            courseModel.updateCourse(course);
                            Toast.makeText(CourseCreate.this, String.format("Curso %s actualizado con éxito!", course.getDescription()), Toast.LENGTH_LONG).show();
                        } catch (NoDataException e) {
                            e.printStackTrace();
                            Toast.makeText(CourseCreate.this, "Error al actualizar el curso.", Toast.LENGTH_LONG).show();
                        } catch (GlobalException e) {
                            Toast.makeText(CourseCreate.this, "Error al actualizar el curso.", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    } else {

                        try {
                            courseModel.insertCourse(course);
                            Toast.makeText(CourseCreate.this, String.format("Curso %s creado con éxito!", course.getDescription()), Toast.LENGTH_LONG).show();
                        } catch (NoDataException e) {
                            e.printStackTrace();
                            Toast.makeText(CourseCreate.this, "Error al insertar el curso.", Toast.LENGTH_LONG).show();
                        } catch (GlobalException e) {
                            e.printStackTrace();
                            Toast.makeText(CourseCreate.this, "Error al insertar el curso.", Toast.LENGTH_LONG).show();
                        }
                    }

                }else {
                    Toast.makeText(CourseCreate.this, "Complete todos los campos del formulario.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public Boolean isValidForm(String id, String description, String credits){
        if (id.isEmpty() || description.isEmpty() || credits.isEmpty()){
            // Is not valid
            return false;
        }
        // Is valid
        return true;
    }
}