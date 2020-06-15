package com.mobile.lab9_10.ui.student;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mobile.lab9_10.R;
import com.mobile.lab9_10.adapters.CourseAdapter;
import com.mobile.lab9_10.adapters.StudentAdapter;
import com.mobile.lab9_10.dataAccess.GlobalException;
import com.mobile.lab9_10.dataAccess.NoDataException;
import com.mobile.lab9_10.entities.Course;
import com.mobile.lab9_10.entities.Student;
import com.mobile.lab9_10.helper.RecyclerItemTouchHelper;
import com.mobile.lab9_10.models.StudentModel;
import com.mobile.lab9_10.ui.course.CourseCreate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StudentFragment extends Fragment implements StudentAdapter.StudentAdapterListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private StudentViewModel studentViewModel;
    private List<Student> studentList;
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private StudentModel studentModel;
    private CoordinatorLayout coordinatorLayout;
    private View root;
    private String contextPath;
    public static final String EDITABLE_FLAG = "editable";
    public static final String STUDENT_FLAG = "student";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        studentViewModel =
                ViewModelProviders.of(this).get(StudentViewModel.class);
        this.root = inflater.inflate(R.layout.fragment_student, container, false);

        // Get Student model instance
        this.contextPath = root.getContext().getFilesDir().getPath();
        this.studentModel = StudentModel.getInstance(this.contextPath);

        // use a linear layout manager
        this.layoutManager = new LinearLayoutManager(root.getContext());
        this.coordinatorLayout = (CoordinatorLayout) root.findViewById(R.id.coordinator_layout_student);

        // This because the content of the recycler will not change between the items
        this.recyclerView = (RecyclerView) root.findViewById(R.id.recycler_student);

        // Create action add course to floating button
        FloatingActionButton fab = this.root.findViewById(R.id.addStudentBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), StudentCreate.class);
                intent.putExtra(EDITABLE_FLAG, false);
                startActivity(intent);
            }
        });

        return this.root;
    }

    public void onResume() {
        super.onResume();

        this.studentList = (List<Student>) this.getCourses();
        this.adapter = new StudentAdapter(this.studentList, this, this.contextPath);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.addItemDecoration(new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL));
        this.recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemSimpleCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemSimpleCallback).attachToRecyclerView(this.recyclerView);

        this.adapter.notifyDataSetChanged();
    }

    private Collection<Student> getCourses(){
        try {
            Collection<Student> studentCollection = this.studentModel.listStudents();
            return studentCollection;
        } catch (NoDataException e) {
            Toast.makeText(root.getContext(), "Error al cargar los estudiantes", Toast.LENGTH_LONG).show();
            Log.e("Students Error", e.toString());
        } catch (GlobalException e) {
            Toast.makeText(root.getContext(), "Error al cargar los estudiantes", Toast.LENGTH_LONG).show();
            Log.e("Students Error", e.toString());
        }
        return new ArrayList<>();
    }

    @Override
    public void onStudentSelected(Student student) {
        Toast.makeText(getContext(), "Seleccionó: " + student.getName(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (direction == ItemTouchHelper.START){
            if (viewHolder instanceof StudentAdapter.MyViewHolder){
                // Save the index deleted
                final int deletedIndex = viewHolder.getAdapterPosition();

                // Get the course to be deleted
                Student student = this.studentList.get(deletedIndex);

                // get the removed item name to display it in snack bar
                String name = student.getName();

                // remove the item from recyclerView
                try {
                    studentModel.deleteStudent(student.getId());
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
                    Toast.makeText(root.getContext(), "Error al intentar eliminar el estudiante", Toast.LENGTH_LONG).show();
                    Log.e("Student Error", e.toString());
                } catch (GlobalException e) {
                    Toast.makeText(root.getContext(), "Error al intentar eliminar el estudiante", Toast.LENGTH_LONG).show();
                    Log.e("Student Error", e.toString());
                }

            }
        } else {

            // If the user is going to edit a course
            Student student = this.adapter.getSwipedItem(viewHolder.getAdapterPosition());

            // Create Intent as editable = true
            Intent intent = new Intent( this.root.getContext(), StudentCreate.class);
            intent.putExtra(EDITABLE_FLAG, true);
            intent.putExtra(STUDENT_FLAG, student);
            this.adapter.notifyDataSetChanged();
            startActivity(intent);
        }
    }

    @Override
    public void onItemMove(int source, int target) {
        this.adapter.onItemMoved(source, target);
    }
}