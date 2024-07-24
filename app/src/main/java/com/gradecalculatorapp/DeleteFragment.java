package com.gradecalculatorapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class DeleteFragment extends Fragment {

    private static final String TAG = "DeleteFragment";
    private EditText courseNameInput;
    private EditText categoryNameInput;
    private RadioGroup deleteOptionGroup;
    private RadioButton deleteCourseOption;
    private RadioButton deleteGradeOption;
    private CourseViewModel courseViewModel;

    public DeleteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete, container, false);

        courseNameInput = view.findViewById(R.id.course_name_input);
        categoryNameInput = view.findViewById(R.id.category_name_input);
        deleteOptionGroup = view.findViewById(R.id.delete_option_group);
        deleteCourseOption = view.findViewById(R.id.delete_course_option);
        deleteGradeOption = view.findViewById(R.id.delete_grade_option);
        Button deleteButton = view.findViewById(R.id.delete_button);

        courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);

        deleteOptionGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.delete_course_option) {
                categoryNameInput.setVisibility(View.GONE);
            } else if (checkedId == R.id.delete_grade_option) {
                categoryNameInput.setVisibility(View.VISIBLE);
            }
        });

        deleteButton.setOnClickListener(v -> {
            String courseName = courseNameInput.getText().toString().trim();
            String categoryName = categoryNameInput.getText().toString().trim();
            if (deleteCourseOption.isChecked()) {
                if (!courseName.isEmpty()) {
                    Log.d(TAG, "Deleting course: " + courseName);
                    courseViewModel.removeCourse(courseName);
                    Toast.makeText(getActivity(), "Course deleted: " + courseName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Please enter the course name.", Toast.LENGTH_SHORT).show();
                }
            } else if (deleteGradeOption.isChecked()) {
                if (!courseName.isEmpty() && !categoryName.isEmpty()) {
                    Course course = courseViewModel.getCourses().getValue().get(courseName);
                    if (course != null) {
                        Log.d(TAG, "Deleting grade: " + categoryName + " from course: " + courseName);
                        Log.d(TAG, "Grades before deletion: " + course.getGrades().size());
                        course.deleteGrade(categoryName);
                        Log.d(TAG, "Grades after deletion: " + course.getGrades().size());
                        if (course.hasGrades()) {
                            courseViewModel.updateCourse(course);
                            Toast.makeText(getActivity(), "Grade deleted: " + categoryName + " from course: " + courseName, Toast.LENGTH_SHORT).show();
                        } else {
                            courseViewModel.removeCourse(courseName);
                            Toast.makeText(getActivity(), "Course deleted: " + courseName + " (no remaining grades)", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Course not found.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please enter both course name and category name.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
