package com.mobile.lab9_10.ui.course;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mobile.lab9_10.R;
import com.mobile.lab9_10.ui.student.CrearStudent;

public class CourseCreate extends AppCompatActivity {

    private EditText id;
    private EditText descripcion;
    private EditText creditos;
    private ImageButton guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_curso);

        this.id = (EditText) findViewById(R.id.inputID);
        this.descripcion = (EditText) findViewById(R.id.inputDescripcion);
        this.creditos = (EditText) findViewById(R.id.inputDescripcion);
        this.guardar = (ImageButton) findViewById(R.id.guardar);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sID = id.getText().toString();
                String vDescripcion = descripcion.getText().toString();
                String sCreditos = creditos.getText().toString();
                if(validateForm(sID,vDescripcion, sCreditos)==0){
                    int vId = Integer.parseInt(sID);
                    int vEdad = Integer.parseInt(sCreditos);
                }else {
                    Toast.makeText(CrearCurso.this, "Complete todos los campos del formulario.", Toast.LENGTH_LONG).show();
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