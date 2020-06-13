package com.mobile.lab9_10.ui.course;

import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.mobile.lab9_10.R;
import com.mobile.lab9_10.adapters.CourseAdapter;
import com.mobile.lab9_10.dataAccess.GlobalException;
import com.mobile.lab9_10.dataAccess.NoDataException;
import com.mobile.lab9_10.entities.Course;
import com.mobile.lab9_10.helper.RecyclerItemTouchHelper;
import com.mobile.lab9_10.models.CourseModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CourseFragment extends Fragment implements CourseAdapter.CourseAdapterListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private CourseViewModel courseViewModel;
    private List<Course> courseList;
    private RecyclerView recyclerView;
    private CourseAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private CourseModel courseModel;
    private CoordinatorLayout coordinatorLayout;
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
        this.courseList = (List<Course>) this.getCourses();
        this.adapter = new CourseAdapter(this.courseList, this);
        this.coordinatorLayout = (CoordinatorLayout) root.findViewById(R.id.coordinator_layoutCa);

        // This because the content of the recycler will not change between the items
        this.recyclerView = (RecyclerView) root.findViewById(R.id.recycler_course);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.addItemDecoration(new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL));
        this.recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemSimpleCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemSimpleCallback).attachToRecyclerView(this.recyclerView);

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
        Toast.makeText(getContext(), "Seleccionó: " + course.getDescription(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (direction == ItemTouchHelper.START){
            if (viewHolder instanceof CourseAdapter.MyViewHolder){
                // Save the index deleted
                final int deletedIndex = viewHolder.getAdapterPosition();

                // Get the course to be deleted
                Course course = this.courseList.get(deletedIndex);

                // get the removed item name to display it in snack bar
                String name = course.getDescription();

                // remove the item from recyclerView
                try {
                    courseModel.deleteCourse(course.getId());
                    this.adapter.removeItem(deletedIndex);

                    // showing snack bar with Undo option
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, name + " removido!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("Deshacer", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adapter.restoreItem(deletedIndex);
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();

                } catch (NoDataException e) {
                    Toast.makeText(root.getContext(), "Error al intentar eliminar el curso", Toast.LENGTH_LONG).show();
                    Log.e("Courses Error", e.toString());
                } catch (GlobalException e) {
                    Toast.makeText(root.getContext(), "Error al intentar eliminar el curso", Toast.LENGTH_LONG).show();
                    Log.e("Courses Error", e.toString());
                }

            }
        }
    }

    @Override
    public void onItemMove(int source, int target) {

    }
}