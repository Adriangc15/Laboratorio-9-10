package com.mobile.lab9_10.ui.course;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.lab9_10.R;
import com.mobile.lab9_10.adapters.CourseAdapter;
import com.mobile.lab9_10.dataAccess.GlobalException;
import com.mobile.lab9_10.dataAccess.NoDataException;
import com.mobile.lab9_10.entities.Course;
import com.mobile.lab9_10.models.CourseModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CourseFragment extends Fragment implements CourseAdapter.CourseAdapterListener, {

    private CourseViewModel courseViewModel;
    private RecyclerView recyclerView;
    private CourseAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private CourseModel courseModel;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.courseViewModel =
                ViewModelProviders.of(this).get(CourseViewModel.class);
        root = inflater.inflate(R.layout.fragment_course, container, false);

        // Get Course model instance
        this.courseModel = CourseModel.getInstance(root.getContext().getFilesDir().getPath());

        // use a linear layout manager
        this.layoutManager = new LinearLayoutManager(root.getContext());
        List<Course> courses = (List<Course>) this.getCourses();
        this.adapter = new CourseAdapter(courses, this);

        // This because the content of the recycler will not change between the items
        this.recyclerView = (RecyclerView) root.findViewById(R.id.recycler_course);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.addItemDecoration(new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL));
        this.recyclerView.setAdapter(adapter);

        //TODO: Implement the helper class

        return root;
    }

    private Collection<Course> getCourses(){
        try {
            Collection<Course> coursesList = this.courseModel.listCourses();
            return coursesList;
        } catch (NoDataException e) {
            Toast.makeText(root.getContext(), "Error al cargar los cursos", Toast.LENGTH_LONG).show();
            Log.e("Courses Error", e.toString());
        } catch (GlobalException e) {
            Toast.makeText(root.getContext(), "Error al cargar los cursos", Toast.LENGTH_LONG).show();
            Log.e("Courses Error", e.toString());
        }
        return new ArrayList<>();
    }

    @Override
    public void onCourseSelected(Course course) {

    }
}