package com.gradecalculatorapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gradecalculatorapp.model.Course;
import com.gradecalculatorapp.viewmodel.CourseViewModel;

public class AddGradeFragment extends Fragment {

    private static final String TAG = "AddGradeFragment";
    private double totalWeightedGrade = 0.0;
    private CourseViewModel courseViewModel;
    private String courseName;
    private int creditHours;

    public AddGradeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_grade, container, false);

        EditText categoryName = view.findViewById(R.id.category_name);
        EditText numericalGrade = view.findViewById(R.id.grade);
        EditText weight = view.findViewById(R.id.weight);
        Button addGradeButton = view.findViewById(R.id.add_grade_button);
        TextView gradeList = view.findViewById(R.id.grade_list);
        TextView totalWeightedGradeText = view.findViewById(R.id.total_weighted_grade);
        Button doneButton = view.findViewById(R.id.done_button);

        courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);

        Bundle args = getArguments();
        if (args != null) {
            courseName = args.getString("courseName");
            creditHours = args.getInt("creditHours");
        }

        addGradeButton.setOnClickListener(v -> {
            String categoryNameInput = categoryName.getText().toString();
            String numericalGradeInput = numericalGrade.getText().toString();
            String weightInput = weight.getText().toString();

            if (!categoryNameInput.isEmpty() && !numericalGradeInput.isEmpty() && !weightInput.isEmpty()) {
                try {
                    double gradeValue = Double.parseDouble(numericalGradeInput);
                    double weightValue = Double.parseDouble(weightInput);

                    gradeList.append("\n" + categoryNameInput + ": " + gradeValue + " (" + weightValue + "%)");

                    totalWeightedGrade += gradeValue * (weightValue / 100);
                    totalWeightedGradeText.setText("Total Weighted Grade: " + totalWeightedGrade);

                    Course course = courseViewModel.getCourses().getValue().get(courseName);
                    if (course == null) {
                        course = new Course(courseName, creditHours, totalWeightedGrade);
                        courseViewModel.addCourse(courseName, course);
                    } else {
                        course.addGrade(categoryNameInput, gradeValue);
                        courseViewModel.updateCourse(course);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Please enter valid numbers for grade and weight", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
            }
        });

        doneButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("courseName", courseName);
            intent.putExtra("creditHours", creditHours);
            intent.putExtra("finalGrade", totalWeightedGrade);
            getActivity().setResult(AppCompatActivity.RESULT_OK, intent);
            getActivity().finish();
        });

        return view;
    }
}
