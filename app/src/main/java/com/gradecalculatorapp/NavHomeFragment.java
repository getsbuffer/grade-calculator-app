package com.gradecalculatorapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.navigation.Navigation;

import com.gradecalculatorapp.model.Course;
import com.gradecalculatorapp.viewmodel.CourseViewModel;

import java.util.Map;

public class NavHomeFragment extends Fragment {

    private static final int ADD_GRADE_REQUEST_CODE = 1;
    private static final String TAG = "NavHomeFragment";
    private double totalGradePoints = 0.0;
    private int totalCreditHours = 0;
    private LinearLayout courseListLayout;
    private TextView totalGPAText;
    private CourseViewModel courseViewModel;

    public NavHomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        EditText courseName = view.findViewById(R.id.course_name);
        EditText creditHours = view.findViewById(R.id.credit_hours);
        Button addCourseButton = view.findViewById(R.id.add_course_button);
        Button deleteCourseButton = view.findViewById(R.id.delete_course_button);
        courseListLayout = view.findViewById(R.id.course_list_layout);
        totalGPAText = view.findViewById(R.id.total_gpa);

        courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);

        addCourseButton.setOnClickListener(v -> {
            String courseNameInput = courseName.getText().toString();
            String creditHoursInput = creditHours.getText().toString();

            if (!courseNameInput.isEmpty() && !creditHoursInput.isEmpty()) {
                try {
                    int creditHoursValue = Integer.parseInt(creditHoursInput);
                    Intent intent = new Intent(getActivity(), AddGradeActivity.class);
                    intent.putExtra("courseName", courseNameInput);
                    intent.putExtra("creditHours", creditHoursValue);
                    startActivityForResult(intent, ADD_GRADE_REQUEST_CODE);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Please enter valid credit hours", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Please enter course name and credit hours", Toast.LENGTH_SHORT).show();
            }
        });

        deleteCourseButton.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.navigation_deleteFragment));

        courseViewModel.getCourses().observe(getViewLifecycleOwner(), courses -> {
            updateCourseList(courses);
            updateGPA();
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
            if (requestCode == ADD_GRADE_REQUEST_CODE) {
                String courseNameInput = data.getStringExtra("courseName");
                int creditHoursValue = data.getIntExtra("creditHours", 0);
                double finalGrade = data.getDoubleExtra("finalGrade", 0.0);

                Course course = courseViewModel.getCourses().getValue().get(courseNameInput);
                if (course == null) {
                    course = new Course(courseNameInput, creditHoursValue, finalGrade);
                    courseViewModel.addCourse(courseNameInput, course);
                } else {
                    course.addGrade(courseNameInput, finalGrade);
                    courseViewModel.updateCourse(course);
                }
            }
        }
    }

    private void updateCourseList(Map<String, Course> courses) {
        courseListLayout.removeAllViews();
        for (Course course : courses.values()) {
            View courseItemView = LayoutInflater.from(getContext()).inflate(R.layout.course_item, courseListLayout, false);
            TextView courseItemText = courseItemView.findViewById(R.id.course_item_text);
            Button editButton = courseItemView.findViewById(R.id.edit_button);
            courseItemText.setText(course.getName() + " - " + course.getFinalGrade() + " (" + course.getCreditHours() + " credit hours)");

            editButton.setOnClickListener(v -> {
                Toast.makeText(getActivity(), "Edit functionality is not available right now.", Toast.LENGTH_SHORT).show();
            });

            courseListLayout.addView(courseItemView);
        }
    }

    private void updateGPA() {
        Map<String, Course> courses = courseViewModel.getCourses().getValue();
        if (courses == null) return;

        totalGradePoints = 0.0;
        totalCreditHours = 0;
        for (Course course : courses.values()) {
            totalCreditHours += course.getCreditHours();
            totalGradePoints += course.getFinalGrade() * course.getCreditHours();
        }

        double totalGPA = totalCreditHours == 0 ? 0 : totalGradePoints / totalCreditHours;

        totalGPAText.setText("Total GPA: " + totalGPA);
    }
}
