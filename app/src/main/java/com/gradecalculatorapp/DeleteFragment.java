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
import android.widget.Toast;

import com.gradecalculatorapp.viewmodel.CourseViewModel;

public class DeleteFragment extends Fragment {

    private static final String TAG = "DeleteFragment";
    private CourseViewModel courseViewModel;
    private EditText courseNameInput;

    public DeleteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete, container, false);

        courseNameInput = view.findViewById(R.id.course_name_input);
        Button deleteButton = view.findViewById(R.id.delete_button);

        courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);

        deleteButton.setOnClickListener(v -> {
            String courseName = courseNameInput.getText().toString().trim();
            if (!courseName.isEmpty()) {
                courseViewModel.removeCourse(courseName);
                Toast.makeText(getActivity(), "Course deleted: " + courseName, Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack();
            } else {
                Toast.makeText(getActivity(), "Please enter the course name.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
