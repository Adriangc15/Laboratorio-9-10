package com.mobile.lab9_10.ui.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.lab9_10.R;
import com.mobile.lab9_10.dataAccess.GlobalException;
import com.mobile.lab9_10.dataAccess.NoDataException;
import com.mobile.lab9_10.entities.Course;
import com.mobile.lab9_10.entities.Student;
import com.mobile.lab9_10.models.CourseModel;
import com.mobile.lab9_10.models.StudentModel;
import com.mobile.lab9_10.ui.course.CourseCreate;
import com.mobile.lab9_10.ui.course.CourseFragment;

import java.util.ArrayList;
import java.util.Collections;

public class StudentCreate extends AppCompatActivity {

    private EditText id;
    private EditText name;
    private EditText lastName;
    private EditText age;
    private ImageButton save;
    private StudentModel studentModel;
    private CourseModel courseModel;
    private Spinner studentCourses;
    private Button enrollBtn;

    public static final String STUDENT_FLAG = "student";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_student);

        this.studentModel = StudentModel.getInstance(getFilesDir().getPath());
        this.courseModel = CourseModel.getInstance(getFilesDir().getPath());

        this.id = (EditText) findViewById(R.id.inputStudentId);
        this.name = (EditText) findViewById(R.id.inputStudentName);
        this.lastName = (EditText) findViewById(R.id.inputStudentLastName);
        this.age = (EditText) findViewById(R.id.inputStudentAge);
        this.save = (ImageButton) findViewById(R.id.saveStudent);
        this.studentCourses = (Spinner) findViewById(R.id.studentCoursesSprinner);
        this.enrollBtn = (Button) findViewById(R.id.matricularBtn);
        final TextView title = (TextView) findViewById(R.id.TitleStudent);

        this.id.setText("");
        this.name.setText("");
        this.lastName.setText("");
        this.age.setText("");

        Bundle extras = getIntent().getExtras();

        final Boolean isEditable = extras.getBoolean(StudentFragment.EDITABLE_FLAG);
        if (isEditable){
            final Student student = (Student) extras.getSerializable(StudentFragment.STUDENT_FLAG);
            if (student != null){
                title.setText("Editando el estudiante: " + student.getName());
                this.id.setEnabled(false);
                this.id.setText(String.valueOf(student.getId()));
                this.name.setText(student.getName());
                this.lastName.setText(student.getLastName());
                this.age.setText(String.valueOf(student.getAge()));
                TableRow coursesRow = (TableRow) findViewById(R.id.coursesRow);
                coursesRow.setVisibility(TableRow.VISIBLE);
                this.enrollBtn.setVisibility(Button.VISIBLE);
                this.loadCursos(student);
                this.enrollBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(StudentCreate.this, StudentEnroll.class);
                        intent.putExtra(STUDENT_FLAG, student);
                        startActivity(intent);
                    }
                });
            }
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sId = id.getText().toString();
                String sName = name.getText().toString();
                String sLastName = lastName.getText().toString();
                String sAge = age.getText().toString();
                if(isValidForm(sId,sName, sLastName, sAge)){
                    Student student = new Student(Integer.parseInt(sId), sName, sLastName, Integer.parseInt(sAge));

                    if (isEditable) {

                        try {
                            studentModel.updateStudent(student);
                            Toast.makeText(StudentCreate.this, String.format("Estudiante %s actualizado con éxito!", student.getName()), Toast.LENGTH_LONG).show();
                        } catch (NoDataException e) {
                            e.printStackTrace();
                            Toast.makeText(StudentCreate.this, "Error al actualizar el estudiante.", Toast.LENGTH_LONG).show();
                        } catch (GlobalException e) {
                            Toast.makeText(StudentCreate.this, "Error al actualizar el estudiante.", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    } else {

                        try {
                            studentModel.insertStudent(student);
                            Toast.makeText(StudentCreate.this, String.format("Estudiante %s creado con éxito!", student.getName()), Toast.LENGTH_LONG).show();
                        } catch (NoDataException e) {
                            e.printStackTrace();
                            Toast.makeText(StudentCreate.this, "Error al insertar el estudiante.", Toast.LENGTH_LONG).show();
                        } catch (GlobalException e) {
                            e.printStackTrace();
                            Toast.makeText(StudentCreate.this, "Error al insertar el estudiante.", Toast.LENGTH_LONG).show();
                        }
                    }

                }else {
                    Toast.makeText(StudentCreate.this, "Complete todos los campos del formulario.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public int validateForm(String nombre, String apellido, String id, String edad){
        if (nombre.isEmpty() || apellido.isEmpty() || id.isEmpty()||edad.isEmpty()){
            return 1;
        }
        return 0;
    }

    public Boolean isValidForm(String id, String name, String lastName, String age){
        if (id.isEmpty() || name.isEmpty() || lastName.isEmpty() || age.isEmpty()){
            // Is not valid
            return false;
        }
        // Is valid
        return true;
    }

    private void loadCursos(Student student){
        try {
            ArrayList<Course> studentCourses = (ArrayList<Course>) this.courseModel.searchStudentCourses(student.getId());
            ArrayAdapter<Course> courseArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, studentCourses);
            courseArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            this.studentCourses.setAdapter(courseArrayAdapter);
        } catch (NoDataException e) {
            e.printStackTrace();
        } catch (GlobalException e) {
            e.printStackTrace();
        }
    }
}