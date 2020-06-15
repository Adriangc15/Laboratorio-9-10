package com.mobile.lab9_10.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.lab9_10.R;
import com.mobile.lab9_10.dataAccess.GlobalException;
import com.mobile.lab9_10.dataAccess.NoDataException;
import com.mobile.lab9_10.entities.Student;
import com.mobile.lab9_10.models.StudentModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> implements Filterable {

    private List<Student> studentList;
    private List<Student> studentListFiltered;
    private Student deletedStudent;
    private StudentAdapterListener listener;
    private StudentModel studentModel;

    public StudentAdapter(List<Student> studentList, StudentAdapterListener listener, String contextPath){
        this.studentList = studentList;
        this.studentListFiltered = studentList;
        this.deletedStudent = null;
        this.listener = listener;
        this.studentModel = StudentModel.getInstance(contextPath);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Student student = studentListFiltered.get(position);
        holder.titleFirstLbl.setText(student.getName());
        holder.titleSecondLbl.setText(student.getLastName());
        holder.descriptionLbl.setText(String.valueOf(student.getId()));
    }

    @Override
    public int getItemCount() {
        return studentListFiltered.size();
    }

    public void removeItem(int position){
        deletedStudent = studentListFiltered.remove(position);
        Iterator<Student> iterator = studentList.iterator();
        while (iterator.hasNext()){
            Student aux = iterator.next();
            if (deletedStudent.equals(aux)){
                iterator.remove();
            }
        }
        notifyItemRemoved(position);
    }

    public void restoreItem(int position){

        try {
            this.studentModel.insertStudent(deletedStudent);
            if (studentListFiltered.size() == studentList.size()){
                studentListFiltered.add(position, deletedStudent);
            } else {
                studentListFiltered.add(position, deletedStudent);
                studentList.add(deletedStudent);
                notifyDataSetChanged();
                notifyItemInserted(position);
            }
        } catch (NoDataException e) {
            e.printStackTrace();
        } catch (GlobalException e) {
            e.printStackTrace();
        }
    }

    public Student getSwipedItem(int position){
        return studentListFiltered.get(position);
    }

    public void onItemMoved(int from, int to){
        if (from < to){
            for (int i = from; i < to; i++){
                Collections.swap(studentListFiltered, i, i + 1);
            }
        } else {
            for (int i = from; i > to; i--){
                Collections.swap(studentListFiltered, i, i - 1);
            }
        }
        notifyItemMoved(from, to);
    }

    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString().toLowerCase();
                if (charString.isEmpty()){
                    studentListFiltered = studentList;
                } else {
                    List<Student> filteredList = new ArrayList<>();
                    for (Student row : studentList){
                        if (String.valueOf(row.getId()).toLowerCase().equals(charString) ||
                                row.getName().toLowerCase().equals(charString) ||
                                row.getLastName().toLowerCase().equals(charString)){
                            filteredList.add(row);
                        }
                    }
                    studentListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = studentListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                studentListFiltered = (ArrayList<Student>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleFirstLbl, titleSecondLbl, descriptionLbl;
        public RelativeLayout viewForeground, viewBackgroundDelete, viewBackgroundEdit;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleFirstLbl = (TextView) itemView.findViewById(R.id.titleFirstLbl);
            titleSecondLbl = (TextView) itemView.findViewById(R.id.titleSecondLbl);
            descriptionLbl = (TextView) itemView.findViewById(R.id.descriptionLbl);

            viewForeground = (RelativeLayout) itemView.findViewById(R.id.view_foreground);
            viewBackgroundEdit = (RelativeLayout) itemView.findViewById(R.id.view_background_edit);
            viewBackgroundDelete = (RelativeLayout) itemView.findViewById(R.id.view_background_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onStudentSelected(studentListFiltered.get(getAdapterPosition()));
                }
            });

        }
    }

    public interface StudentAdapterListener {
        void onStudentSelected(Student student);
    }

}
