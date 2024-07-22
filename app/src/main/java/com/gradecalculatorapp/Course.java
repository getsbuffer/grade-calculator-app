package com.gradecalculatorapp;

import java.util.HashMap;
import java.util.Map;

public class Course {
    private String name;
    private int creditHours;
    private double finalGrade;
    private Map<String, Double> grades = new HashMap<>();

    public Course(String name, int creditHours, double finalGrade) {
        this.name = name;
        this.creditHours = creditHours;
        this.finalGrade = finalGrade;
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

    public void addGrade(String category, double grade) {
        grades.put(category, grade);
    }

    public void deleteGrade(String category) {
        grades.remove(category);
    }

    public Map<String, Double> getGrades() {
        return grades;
    }
}
