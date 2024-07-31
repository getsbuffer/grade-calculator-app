package com.gradecalculatorapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gradecalculatorapp.Adapter.GradesAdapter;
import com.gradecalculatorapp.model.Course;
import com.gradecalculatorapp.model.GradeDetail;
import com.gradecalculatorapp.viewmodel.CourseViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddGradeFragment extends Fragment {

    private static final String TAG = "AddGradeFragment";
    private double totalWeightedGrade = 0.0;
    private CourseViewModel courseViewModel;
    private String courseName;
    private int creditHours;
    private RecyclerView gradesRecyclerView;
    private GradesAdapter gradesAdapter;
    private List<GradeItem> gradeItems = new ArrayList<>();

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
        gradesRecyclerView = view.findViewById(R.id.grades_recycler_view);

        courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);

        gradesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        gradesAdapter = new GradesAdapter(gradeItems, this::removeGrade);
        gradesRecyclerView.setAdapter(gradesAdapter);

        Bundle args = getArguments();
        if (args != null) {
            courseName = args.getString("courseName");
            creditHours = args.getInt("creditHours");

            Course course = courseViewModel.getCourses().getValue().get(courseName);
            if (course != null) {
                totalWeightedGrade = course.getFinalGrade();
                totalWeightedGradeText.setText(String.format("Total Weighted Grade: %.2f", totalWeightedGrade));

                for (Map.Entry<String, GradeDetail> entry : course.getGrades().entrySet()) {
                    String category = entry.getKey();
                    GradeDetail detail = entry.getValue();
                    addGradeToList(category, roundToTwoDecimalPlaces(detail.getValue()), roundToTwoDecimalPlaces(detail.getWeight()));
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

                    gradeValue = roundToTwoDecimalPlaces(gradeValue);
                    weightValue = roundToTwoDecimalPlaces(weightValue);

                    addGradeToList(categoryNameInput, gradeValue, weightValue);

                    categoryName.setText("");
                    numericalGrade.setText("");
                    weight.setText("");

                    totalWeightedGrade += gradeValue * (weightValue / 100);
                    totalWeightedGradeText.setText(String.format("Total Weighted Grade: %.2f", totalWeightedGrade));

                    Course course = courseViewModel.getCourses().getValue().get(courseName);
                    if (course == null) {
                        course = new Course(courseName, creditHours, totalWeightedGrade);
                        course.addGrade(categoryNameInput, gradeValue, weightValue);
                        courseViewModel.addCourse(courseName, course);
                    } else {
                        course.addGrade(categoryNameInput, gradeValue, weightValue);
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
        GradeItem gradeItem = new GradeItem(categoryName, grade, weight);
        gradeItems.add(gradeItem);
        gradesAdapter.notifyItemInserted(gradeItems.size() - 1);
    }

    private void removeGrade(GradeItem gradeItem) {
        totalWeightedGrade -= gradeItem.getGrade() * (gradeItem.getWeight() / 100);
        gradeItems.remove(gradeItem);
        gradesAdapter.notifyDataSetChanged();

        TextView totalWeightedGradeText = getView().findViewById(R.id.total_weighted_grade);
        totalWeightedGradeText.setText(String.format("Total Weighted Grade: %.2f", totalWeightedGrade));

        Course course = courseViewModel.getCourses().getValue().get(courseName);
        if (course != null) {
            course.deleteGrade(gradeItem.getCategoryName());
            courseViewModel.updateCourse(course);
        }
    }

    private double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public static class GradeItem {
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
