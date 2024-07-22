package com.gradecalculatorapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class DeleteFragment extends Fragment {

    private EditText courseNameInput;
    private EditText categoryNameInput;
    private RadioGroup deleteOptionGroup;
    private RadioButton deleteCourseOption;
    private RadioButton deleteGradeOption;

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

        deleteOptionGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.delete_course_option) {
                categoryNameInput.setVisibility(View.GONE);
            } else if (checkedId == R.id.delete_grade_option) {
                categoryNameInput.setVisibility(View.VISIBLE);
            }
        });

        deleteButton.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Delete functionality is not available right now.", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
