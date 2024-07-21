package com.gradecalculatorapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddCourseFragment extends Fragment {

    public AddCourseFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_grade, container, false);

        EditText categoryName = view.findViewById(R.id.category_name);
        EditText grade = view.findViewById(R.id.grade);
        EditText weight = view.findViewById(R.id.weight);
        Button addGradeButton = view.findViewById(R.id.add_grade_button);
        TextView gradeList = view.findViewById(R.id.grade_list);
        TextView totalWeightedGrade = view.findViewById(R.id.total_weighted_grade);

        addGradeButton.setOnClickListener(v -> {
            String categoryNameInput = categoryName.getText().toString();
            String gradeInput = grade.getText().toString();
            String weightInput = weight.getText().toString();

            if (!categoryNameInput.isEmpty() && !gradeInput.isEmpty() && !weightInput.isEmpty()) {
                try {
                    double gradeValue = Double.parseDouble(gradeInput);
                    double weightValue = Double.parseDouble(weightInput);
                    gradeList.append("\n" + categoryNameInput + ": " + gradeValue + " (" + weightValue + "%)");
                    double currentTotal = Double.parseDouble(totalWeightedGrade.getText().toString().split(": ")[1]);
                    currentTotal += gradeValue * (weightValue / 100);
                    totalWeightedGrade.setText("Total Weighted Grade: " + currentTotal);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Please enter valid numbers for grade and weight", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
