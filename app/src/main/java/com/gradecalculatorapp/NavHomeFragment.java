package com.gradecalculatorapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gradecalculatorapp.adapter.CourseAdapter;
import com.gradecalculatorapp.model.Course;
import com.gradecalculatorapp.viewmodel.CourseViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NavHomeFragment extends Fragment {

    private double totalGradePoints = 0.0;
    private int totalCreditHours = 0;
    private RecyclerView courseRecyclerView;

    private TextView totalGPAText;
    private CourseViewModel courseViewModel;
    private CourseAdapter courseAdapter;
    private List<Course> courseList = new ArrayList<>();

    public NavHomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        EditText courseName = view.findViewById(R.id.course_name);
        EditText creditHours = view.findViewById(R.id.credit_hours);
        Button addCourseButton = view.findViewById(R.id.add_course_button);
        Button deleteCourseButton = view.findViewById(R.id.delete_course_button);
        courseRecyclerView = view.findViewById(R.id.course_recycler_view);
        totalGPAText = view.findViewById(R.id.total_gpa);

        // Initialize ViewModel
        courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);

        // Setup RecyclerView
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        courseAdapter = new CourseAdapter(courseList, course -> navigateToAddGradeFragment(course.getName(), course.getCreditHours()));
        courseRecyclerView.setAdapter(courseAdapter);

        // Add course button listener
        addCourseButton.setOnClickListener(v -> {
            String courseNameInput = courseName.getText().toString();
            String creditHoursInput = creditHours.getText().toString();

            if (!courseNameInput.isEmpty() && !creditHoursInput.isEmpty()) {
                try {
                    int creditHoursValue = Integer.parseInt(creditHoursInput);
                    Course course = new Course(courseNameInput, "Null", creditHoursValue, 0.0);
                    courseViewModel.addCourse(courseNameInput, course);
                    navigateToAddGradeFragment(courseNameInput, creditHoursValue);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Please enter valid credit hours", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Please enter course name and credit hours", Toast.LENGTH_SHORT).show();
            }
        });

        // Delete course button listener
        deleteCourseButton.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.navigation_deleteFragment));

        // Observe changes in course list
        courseViewModel.getCourses().observe(getViewLifecycleOwner(), courses -> {
            courseName.setText("");
            creditHours.setText("");
            updateCourseList(courses);
            updateGPA();
        });

        return view;
    }

    private void navigateToAddGradeFragment(String courseName, int creditHours) {
        Bundle args = new Bundle();
        args.putString("courseName", courseName);
        args.putInt("creditHours", creditHours);
        Navigation.findNavController(requireView()).navigate(R.id.action_home_to_addGrade, args);
    }

    private void updateCourseList(Map<String, Course> courses) {
        courseList.clear();
        courseList.addAll(courses.values());
        courseAdapter.notifyDataSetChanged();
    }

    private void updateGPA() {
        Map<String, Course> courses = courseViewModel.getCourses().getValue();
        String letterGrade;
        if (courses == null) return;

        totalGradePoints = 0.0;
        totalCreditHours = 0;

        for (Course course : courses.values()) {
            letterGrade = course.calculateLetterGrade(course.getFinalGrade());
            double gradePoints;

            switch (letterGrade) {
                case "A":
                    gradePoints = 4.0;
                    break;
                case "A-":
                    gradePoints = 3.75;
                    break;
                case "B+":
                    gradePoints = 3.25;
                    break;
                case "B":
                    gradePoints = 3.0;
                    break;
                case "B-":
                    gradePoints = 2.75;
                    break;
                case "C+":
                    gradePoints = 2.25;
                    break;
                case "C":
                    gradePoints = 2.0;
                    break;
                case "C-":
                    gradePoints = 1.75;
                    break;
                case "D+":
                    gradePoints = 1.25;
                    break;
                case "D":
                    gradePoints = 1.0;
                    break;
                case "D-":
                    gradePoints = 0.75;
                    break;
                default:
                    gradePoints = 0.0;
                    break;
            }

            totalCreditHours += course.getCreditHours();
            totalGradePoints += gradePoints * course.getCreditHours();
        }

        if (totalCreditHours != 0) {
            double totalGPA = totalGradePoints / totalCreditHours;
            totalGPA = roundToTwoDecimalPlaces(totalGPA);
            totalGPAText.setText(String.format("Total GPA: %.2f", totalGPA));
        } else {
            totalGPAText.setText("Total GPA: 0.00");
        }
    }

    private double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
