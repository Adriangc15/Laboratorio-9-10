package com.mobile.lab9_10.ui.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mobile.lab9_10.R;

public class CrearStudent extends AppCompatActivity {

    private EditText id;
    private EditText nombre;
    private EditText apellido;
    private EditText edad;
    private ImageButton guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_student);

        this.id = (EditText) findViewById(R.id.inputCourseId);
        this.nombre = (EditText) findViewById(R.id.inputNombre);
        this.apellido = (EditText) findViewById(R.id.inputApellido);
        this.edad = (EditText) findViewById(R.id.inputEdad);
        this.guardar = (ImageButton) findViewById(R.id.saveCourse);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sID = id.getText().toString();
                String sEdad = edad.getText().toString();
                String vNombre = nombre.getText().toString();
                String vApellido = apellido.getText().toString();
                if(validateForm(vNombre,vApellido, sID, sEdad)==0){
                    int vId = Integer.parseInt(sID);
                    int vEdad = Integer.parseInt(sEdad);
                }else {
                    Toast.makeText(CrearStudent.this, "Complete todos los campos del formulario.", Toast.LENGTH_LONG).show();
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
}