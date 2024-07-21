package com.gradecalculatorapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddClassFragment extends Fragment {

    public AddClassFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_grade, container, false);

        EditText className = view.findViewById(R.id.class_name);
        EditText creditHours = view.findViewById(R.id.credit_hours);
        Button addClassButton = view.findViewById(R.id.add_class_button);
        TextView totalGPA = view.findViewById(R.id.total_gpa);

        addClassButton.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Testing AddClassFragment", Toast.LENGTH_SHORT).show();

        });

        return view;
    }
}
