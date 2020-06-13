package com.mobile.lab9_10.ui.course;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mobile.lab9_10.R;

public class CourseCreate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_curso);

        Bundle extras = getIntent().getExtras();
        if (extras.getBoolean(CourseFragment.EDITABLE_FLAG)){
            
        }

    }
}