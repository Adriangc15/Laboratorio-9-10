package com.mobile.lab9_10.adapters;


import android.security.keystore.SecureKeyImportUnavailableException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.lab9_10.R;
import com.mobile.lab9_10.entities.Course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> implements Filterable {

    private List<Course> courseList;
    private List<Course> courseListFiltered;
    private CourseAdapterListener listener;
    private Course deletedCourse;

    public CourseAdapter(List<Course> courseList, CourseAdapterListener listener){
        this.courseList = courseList;
        this.courseListFiltered = courseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Course course = courseListFiltered.get(position);
        holder.titleFirstLbl.setText(course.getDescription());
        holder.titleSecondLbl.setText(course.getCredits());
        holder.descriptionLbl.setText(String.valueOf(course.getId()));
    }

    @Override
    public int getItemCount() {
        return courseListFiltered.size();
    }

    public void removeItem(int position){
        deletedCourse = courseListFiltered.remove(position);
        Iterator<Course> iterator = courseList.iterator();
        while (iterator.hasNext()){
            Course aux = iterator.next();
            if (deletedCourse.equals(aux)){
                iterator.remove();
            }
        }
        notifyItemRemoved(position);
    }

    public void restoreItem(int position){
        if (courseListFiltered.size() == courseList.size()){
            courseListFiltered.add(position, deletedCourse);
        } else {
            courseListFiltered.add(position, deletedCourse);
            courseList.add(deletedCourse);
        }

        notifyDataSetChanged();
        notifyItemInserted(position);
    }

    public Course getSwipedItem(int position){
        return courseListFiltered.get(position);
    }

    public void onItemMoved(int from, int to){
        if (from < to){
            for (int i = from; i < to; i++){
                Collections.swap(courseListFiltered, i, i + 1);
            }
        } else {
            for (int i = from; i > to; i--){
                Collections.swap(courseListFiltered, i, i - 1);
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
                    courseListFiltered = courseList;
                } else {
                    List<Course> filteredList = new ArrayList<>();
                    for (Course row : courseList){
                        if (String.valueOf(row.getId()).toLowerCase().equals(charString) ||
                            row.getCredits().toLowerCase().equals(charString) ||
                            row.getDescription().toLowerCase().equals(charString)){
                            filteredList.add(row);
                        }
                    }
                    courseListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = courseListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                courseListFiltered = (ArrayList<Course>) results.values;
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
                    listener.onCourseSelected(courseListFiltered.get(getAdapterPosition()));
                }
            });

        }
    }

    public interface CourseAdapterListener {
        void onCourseSelected(Course course);
    }
}
