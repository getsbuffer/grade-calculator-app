package com.gradecalculatorapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gradecalculatorapp.R;
import com.gradecalculatorapp.model.Course;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course> courseList;
    private OnEditButtonClickListener editButtonClickListener;

    public interface OnEditButtonClickListener {
        void onEditButtonClick(Course course);
    }

    public CourseAdapter(List<Course> courseList, OnEditButtonClickListener listener) {
        this.courseList = courseList;
        this.editButtonClickListener = listener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);
        String letterGrade = course.calculateLetterGrade(course.getFinalGrade());
        holder.courseItemText.setText(course.getName() + " - " + course.getFinalGrade() + " (" + letterGrade + ") (" + course.getCreditHours() + " credit hours)");
        holder.editButton.setOnClickListener(v -> editButtonClickListener.onEditButtonClick(course));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView courseItemText;
        Button editButton;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseItemText = itemView.findViewById(R.id.course_item_text);
            editButton = itemView.findViewById(R.id.edit_button);
        }
    }
}
