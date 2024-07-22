package com.gradecalculatorapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddGradeFragment extends Fragment {

    private double totalWeightedGrade = 0.0;

    public AddGradeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_grade, container, false);

        EditText categoryName = view.findViewById(R.id.category_name);
        EditText grade = view.findViewById(R.id.grade);
        EditText weight = view.findViewById(R.id.weight);
        Button addGradeButton = view.findViewById(R.id.add_grade_button);
        TextView gradeList = view.findViewById(R.id.grade_list);
        TextView totalWeightedGradeText = view.findViewById(R.id.total_weighted_grade);
        Button doneButton = view.findViewById(R.id.done_button);

        addGradeButton.setOnClickListener(v -> {
            String categoryNameInput = categoryName.getText().toString();
            String gradeInput = grade.getText().toString();
            String weightInput = weight.getText().toString();

            if (!categoryNameInput.isEmpty() && !gradeInput.isEmpty() && !weightInput.isEmpty()) {
                try {
                    double gradeValue = Double.parseDouble(gradeInput);
                    double weightValue = Double.parseDouble(weightInput);
                    // Add grade to list (here you would normally update a list and recalculate weighted grade)
                    gradeList.append("\n" + categoryNameInput + ": " + gradeValue + " (" + weightValue + "%)");
                    // Calculate new total weighted grade (this is simplified for demonstration)
                    totalWeightedGrade += gradeValue * (weightValue / 100);
                    totalWeightedGradeText.setText("Total Weighted Grade: " + totalWeightedGrade);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Please enter valid numbers for grade and weight", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
            }
        });

        doneButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("courseName", getArguments().getString("courseName"));
            intent.putExtra("creditHours", getArguments().getInt("creditHours"));
            intent.putExtra("finalGrade", totalWeightedGrade);
            getActivity().setResult(AppCompatActivity.RESULT_OK, intent);
            getActivity().finish();
        });

        return view;
    }
}
