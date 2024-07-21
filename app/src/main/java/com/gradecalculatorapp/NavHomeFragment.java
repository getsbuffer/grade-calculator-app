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

public class NavHomeFragment extends Fragment {

    public NavHomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        EditText courseName = view.findViewById(R.id.course_name);
        EditText creditHours = view.findViewById(R.id.credit_hours);
        Button addCourseButton = view.findViewById(R.id.add_course_button);
        TextView courseList = view.findViewById(R.id.course_list);
        TextView totalGPA = view.findViewById(R.id.total_gpa);

        addCourseButton.setOnClickListener(v -> {
            String courseNameInput = courseName.getText().toString();
            String creditHoursInput = creditHours.getText().toString();

            if (!courseNameInput.isEmpty() && !creditHoursInput.isEmpty()) {
                try {
                    int creditHoursValue = Integer.parseInt(creditHoursInput);
                    courseList.append("\n" + courseNameInput + " (" + creditHoursValue + " credit hours)");
                    // Navigate to AddGradeFragment
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.action_home_to_addClass);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Please enter valid credit hours", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Please enter course name and credit hours", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
