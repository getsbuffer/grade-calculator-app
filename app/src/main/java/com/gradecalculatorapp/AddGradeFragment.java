package com.gradecalculatorapp;

import android.os.Bundle;
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

import com.gradecalculatorapp.model.Course;
import com.gradecalculatorapp.viewmodel.CourseViewModel;

import java.util.Map;

public class AddGradeFragment extends Fragment {

    private static final String TAG = "AddGradeFragment";
    private double totalWeightedGrade = 0.0;
    private CourseViewModel courseViewModel;
    private String courseName;
    private int creditHours;
    private LinearLayout gradeListLayout;

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
        TextView totalWeightedGradeText = view.findViewById(R.id.total_weighted_grade);
        Button doneButton = view.findViewById(R.id.done_button);
        gradeListLayout = view.findViewById(R.id.grade_list_layout);

        courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);

        Bundle args = getArguments();
        if (args != null) {
            courseName = args.getString("courseName");
            creditHours = args.getInt("creditHours");

            Course course = courseViewModel.getCourses().getValue().get(courseName);
            if (course != null) {
                totalWeightedGrade = course.getFinalGrade();
                totalWeightedGradeText.setText("Total Weighted Grade: " + totalWeightedGrade);

                for (Map.Entry<String, Double> grade : course.getGrades().entrySet()) {
                    addGradeToList(grade.getKey(), grade.getValue(), 100.0 / course.getGrades().size());
                }
            }
        }

        addGradeButton.setOnClickListener(v -> {
            String categoryNameInput = categoryName.getText().toString();
            String numericalGradeInput = numericalGrade.getText().toString();
            String weightInput = weight.getText().toString();

            if (!categoryNameInput.isEmpty() && !numericalGradeInput.isEmpty() && !weightInput.isEmpty()) {
                try {
                    double gradeValue = Double.parseDouble(numericalGradeInput);
                    double weightValue = Double.parseDouble(weightInput);

                    addGradeToList(categoryNameInput, gradeValue, weightValue);

                    categoryName.setText("");
                    numericalGrade.setText("");
                    weight.setText("");

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
            getActivity().onBackPressed();
        });

        return view;
    }

    private void addGradeToList(String categoryName, double grade, double weight) {
        View gradeItemView = LayoutInflater.from(getContext()).inflate(R.layout.grade_item, gradeListLayout, false);
        TextView gradeItemText = gradeItemView.findViewById(R.id.grade_item_text);
        Button deleteGradeButton = gradeItemView.findViewById(R.id.delete_grade_button);

        gradeItemText.setText(categoryName + ": " + grade + " (" + weight + "%)");
        gradeItemView.setTag(new GradeItem(categoryName, grade, weight));

        deleteGradeButton.setOnClickListener(v -> {
            GradeItem gradeItem = (GradeItem) gradeItemView.getTag();
            totalWeightedGrade -= gradeItem.getGrade() * (gradeItem.getWeight() / 100);
            ((ViewGroup) gradeItemView.getParent()).removeView(gradeItemView);
            TextView totalWeightedGradeText = getView().findViewById(R.id.total_weighted_grade);
            totalWeightedGradeText.setText("Total Weighted Grade: " + totalWeightedGrade);

            Course course = courseViewModel.getCourses().getValue().get(courseName);
            if (course != null) {
                course.deleteGrade(gradeItem.getCategoryName());
                courseViewModel.updateCourse(course);
            }
        });

        gradeListLayout.addView(gradeItemView);
    }

    private static class GradeItem {
        private String categoryName;
        private double grade;
        private double weight;

        public GradeItem(String categoryName, double grade, double weight) {
            this.categoryName = categoryName;
            this.grade = grade;
            this.weight = weight;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public double getGrade() {
            return grade;
        }

        public double getWeight() {
            return weight;
        }
    }
}
