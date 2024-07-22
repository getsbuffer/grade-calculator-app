package com.gradecalculatorapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NavHomeFragment extends Fragment {

    private static final int ADD_GRADE_REQUEST_CODE = 1;
    private double totalGradePoints = 0.0;
    private int totalCreditHours = 0;

    public NavHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        EditText courseName = view.findViewById(R.id.course_name);
        EditText creditHours = view.findViewById(R.id.credit_hours);
        Button addCourseButton = view.findViewById(R.id.add_course_button);
        TextView courseList = view.findViewById(R.id.course_list);
        TextView totalGPAText = view.findViewById(R.id.total_gpa);

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

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_GRADE_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            if (data != null) {
                String courseNameInput = data.getStringExtra("courseName");
                int creditHoursValue = data.getIntExtra("creditHours", 0);
                double finalGrade = data.getDoubleExtra("finalGrade", 0.0);

                totalCreditHours += creditHoursValue;
                double gradePoint = calculateGradePoint(finalGrade);
                totalGradePoints += gradePoint * creditHoursValue;

                double totalGPA = totalGradePoints / totalCreditHours;

                TextView courseList = getView().findViewById(R.id.course_list);
                TextView totalGPAText = getView().findViewById(R.id.total_gpa);
                courseList.append("\n" + courseNameInput + " - " + finalGrade + " (" + creditHoursValue + " credit hours)");
                totalGPAText.setText("Total GPA: " + totalGPA);
            }
        }
    }

    private double calculateGradePoint(double finalGrade) {
        if (finalGrade >= 90) {
            return 4.0;
        } else if (finalGrade >= 80) {
            return 3.0;
        } else if (finalGrade >= 70) {
            return 2.0;
        } else if (finalGrade >= 60) {
            return 1.0;
        } else {
            return 0.0;
        }
    }
}
