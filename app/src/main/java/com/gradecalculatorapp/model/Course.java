package com.gradecalculatorapp.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Course implements Serializable {
    private String name;
    private int creditHours;
    private double finalGrade;
    private Map<String, Double> grades;

    public Course(String name, int creditHours, double finalGrade) {
        this.name = name;
        this.creditHours = creditHours;
        this.finalGrade = finalGrade;
        this.grades = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public double getFinalGrade() {
        return finalGrade;
    }

    public Map<String, Double> getGrades() {
        return grades;
    }

    public void addGrade(String category, double grade) {
        grades.put(category, grade);
        updateFinalGrade();
    }

    public void deleteGrade(String category) {
        grades.remove(category);
        updateFinalGrade();
    }

    private void updateFinalGrade() {
        if (grades.isEmpty()) {
            finalGrade = 0.0;
        } else {
            double total = 0.0;
            for (double grade : grades.values()) {
                total += grade;
            }
            finalGrade = total / grades.size();
        }
    }
}
