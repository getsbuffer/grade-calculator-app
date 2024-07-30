package com.gradecalculatorapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gradecalculatorapp.R;
import com.gradecalculatorapp.AddGradeFragment.GradeItem;

import java.util.List;

public class GradesAdapter extends RecyclerView.Adapter<GradesAdapter.GradeViewHolder> {

    private List<GradeItem> gradeItems;
    private OnGradeDeleteListener onGradeDeleteListener;

    public GradesAdapter(List<GradeItem> gradeItems, OnGradeDeleteListener onGradeDeleteListener) {
        this.gradeItems = gradeItems;
        this.onGradeDeleteListener = onGradeDeleteListener;
    }

    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grade_item, parent, false);
        return new GradeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
        GradeItem gradeItem = gradeItems.get(position);
        holder.gradeItemText.setText(gradeItem.getCategoryName() + ": " + gradeItem.getGrade() + " (" + gradeItem.getWeight() + "%)");

        holder.deleteGradeButton.setOnClickListener(v -> {
            onGradeDeleteListener.onGradeDelete(gradeItem);
        });
    }

    @Override
    public int getItemCount() {
        return gradeItems.size();
    }

    public static class GradeViewHolder extends RecyclerView.ViewHolder {
        TextView gradeItemText;
        Button deleteGradeButton;

        public GradeViewHolder(@NonNull View itemView) {
            super(itemView);
            gradeItemText = itemView.findViewById(R.id.grade_item_text);
            deleteGradeButton = itemView.findViewById(R.id.delete_grade_button);
        }
    }

    public interface OnGradeDeleteListener {
        void onGradeDelete(GradeItem gradeItem);
    }
}
