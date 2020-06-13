package com.mobile.lab9_10.ui.course;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.lab9_10.R;
import com.mobile.lab9_10.entities.Course;

public class CourseCreate extends AppCompatActivity {

    private EditText id;
    private EditText description;
    private EditText credits;
    private ImageButton save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_curso);

        this.id = (EditText) findViewById(R.id.inputCourseId);
        this.description = (EditText) findViewById(R.id.inputCourseDescription);
        this.credits = (EditText) findViewById(R.id.inputCourseCredits);
        this.save = (ImageButton) findViewById(R.id.saveCourse);
        TextView title = (TextView) findViewById(R.id.TitleCourse);

        this.id.setText("");
        this.description.setText("");
        this.credits.setText("");

        Bundle extras = getIntent().getExtras();
        if (extras.getBoolean(CourseFragment.EDITABLE_FLAG)){
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

                String sID = id.getText().toString();
                String vDescripcion = description.getText().toString();
                String sCreditos = credits.getText().toString();
                if(validateForm(sID,vDescripcion, sCreditos)==0){
                    int vId = Integer.parseInt(sID);
                    int vEdad = Integer.parseInt(sCreditos);
                }else {
                    Toast.makeText(CourseCreate.this, "Complete todos los campos del formulario.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public int validateForm(String id, String descripcion, String creditos){
        if (id.isEmpty() || descripcion.isEmpty() || creditos.isEmpty()){
            return 1;
        }
        return 0;
    }
}