package com.gradecalculatorapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddGradeFragment extends Fragment {

    private static final String TAG = "AddGradeFragment";
    private double totalWeightedGrade = 0.0;
    private CourseViewModel courseViewModel;
    private String courseName;
    private int creditHours;

    public AddGradeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
            Log.d(TAG, "Received courseName: " + courseName + ", creditHours: " + creditHours);
        } else {
            Log.e(TAG, "No arguments received");
        }

        addGradeButton.setOnClickListener(v -> {
            String categoryNameInput = categoryName.getText().toString();
            String numericalGradeInput = numericalGrade.getText().toString();
            String weightInput = weight.getText().toString();

            if (!categoryNameInput.isEmpty() && !numericalGradeInput.isEmpty() && !weightInput.isEmpty()) {
                try {
                    double gradeValue = Double.parseDouble(numericalGradeInput);
                    double weightValue = Double.parseDouble(weightInput);

                    // Add grade to list
                    gradeList.append("\n" + categoryNameInput + ": " + gradeValue + " (" + weightValue + "%)");

                    // Calculate new total weighted grade (this is simplified for demonstration)
                    totalWeightedGrade += gradeValue * (weightValue / 100);
                    totalWeightedGradeText.setText("Total Weighted Grade: " + totalWeightedGrade);
                    Log.d(TAG, "Added grade: " + gradeValue + " with weight: " + weightValue + " to category: " + categoryNameInput);

                    // Update the course in ViewModel
                    Course course = courseViewModel.getCourses().getValue().get(courseName);
                    if (course == null) {
                        course = new Course(courseName, creditHours, totalWeightedGrade);
                        Log.d(TAG, "Created new course: " + courseName);
                    }
                    course.addGrade(categoryNameInput, gradeValue);
                    Log.d(TAG, "Current grades in course: " + course.getGrades().size());
                    courseViewModel.updateCourse(course);
                    Log.d(TAG, "Updated course in ViewModel: " + courseName);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Please enter valid numbers for grade and weight", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Invalid number format for grade or weight", e);
                }
            } else {
                Toast.makeText(getActivity(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Incomplete input fields");
            }
        });

        doneButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("courseName", courseName);
            intent.putExtra("creditHours", creditHours);
            intent.putExtra("finalGrade", totalWeightedGrade);
            getActivity().setResult(AppCompatActivity.RESULT_OK, intent);
            getActivity().finish();
            Log.d(TAG, "Done button clicked, finishing activity");
        });

        return view;
    }
}
