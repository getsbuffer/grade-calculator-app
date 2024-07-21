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

public class AddGradeFragment extends Fragment {

    public AddGradeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_history, container, false);

        EditText categoryName = view.findViewById(R.id.category_name);
        EditText grade = view.findViewById(R.id.grade);
        EditText weight = view.findViewById(R.id.weight);
        Button addGradeButton = view.findViewById(R.id.add_grade_button);
        TextView weightedGrade = view.findViewById(R.id.weighted_grade);

        addGradeButton.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Testing AddGradeFragment", Toast.LENGTH_SHORT).show();

        });

        return view;
    }
}
